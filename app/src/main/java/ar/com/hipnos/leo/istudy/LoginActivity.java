package ar.com.hipnos.leo.istudy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ar.com.hipnos.leo.istudy.api.ApiService;
import ar.com.hipnos.leo.istudy.api.ErrorService;
import ar.com.hipnos.leo.istudy.api.response.LoginResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.usuario)
    EditText usuario;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    Button loginButton;

    @BindView(R.id.register)
    Button registerButton;

    @BindView(R.id.loading_login)
    ProgressBar loading_login;

    @BindView(R.id.loading_register)
    ProgressBar loading_register;

    @BindView(R.id.error_message)
    TextView error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void register(){

        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        //i.putExtra("variable","valor");// si quiero parsar datos a traves del intent
        startActivity(i);
    }

    @OnClick(R.id.login)
    public void login(){

        loginButton.setEnabled(false);
        loading_login.setVisibility(View.VISIBLE);

        String usuario_ingresado = this.usuario.getText().toString();
        String password_ingresado = this.password.getText().toString();

        if(usuario_ingresado.isEmpty()){
            this.usuario.setError(getString(R.string.campo_obligatorio));
        }else{
            this.usuario.setError(null);//borra el error
            ApiService.login(usuario_ingresado, password_ingresado, new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()){
                        Log.i(TAG, response.message());

                        LoginResponse body = response.body();

                        SharedPreferences.Editor editor = getSharedPreferences("token", MODE_PRIVATE)
                                .edit();

                        editor.putString("token_type", body.getToken_type());
                        editor.putString("access_token", body.getAccess_token());
                        editor.putString("refresh_token", body.getRefresh_token());
                        editor.apply();

                        loginButton.setEnabled(true);
                        loading_login.setVisibility(View.INVISIBLE);

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                    }else{

                        String error = response.message().equals("Unauthorized")?"Las credenciales proporcionadas no son validas":"Se produjo un error, intente nuevamente";
                        ErrorService.showError(error_message, error );

                        loginButton.setEnabled(true);
                        loading_login.setVisibility(View.INVISIBLE);

                        Log.e(TAG,response.message());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Log.d(TAG,t.getLocalizedMessage());

                    ErrorService.showError(error_message, "No fue posible cominicarse con el servidor. Verifique si tiene internet." );

                    loginButton.setEnabled(true);
                    loading_login.setVisibility(View.INVISIBLE);
                }
            });
        }


    }
}
