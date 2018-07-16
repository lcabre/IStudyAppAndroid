package ar.com.hipnos.leo.istudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ar.com.hipnos.leo.istudy.api.ApiService;
import ar.com.hipnos.leo.istudy.api.ErrorService;
import ar.com.hipnos.leo.istudy.api.response.RegistrationResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.usuario)
    EditText usuario;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.register)
    Button registerButton;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.error_message)
    TextView error_message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        TextView tx = (TextView)findViewById(R.id.titulo);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LeagueSpartan-Bold.otf");
        tx.setTypeface(custom_font);
    }

    @OnClick(R.id.register)
    public void clickRegister(){
        String usuario_ingresado = this.usuario.getText().toString();
        String password_ingresado = this.password.getText().toString();

        if(usuario_ingresado.isEmpty()) {
            this.usuario.setError(getString(R.string.campo_obligatorio));
        }else if(password_ingresado.isEmpty()){
            this.password.setError(getString(R.string.campo_obligatorio));
        }else {
            this.usuario.setError(null);//borra el error
            this.password.setError(null);

            registerButton.setEnabled(false);
            loading.setVisibility(View.VISIBLE);

            Log.d(TAG, "registrando conusuario: " + usuario_ingresado + " password: " + password_ingresado);

            ApiService.register(usuario_ingresado, password_ingresado, new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, response.message());

                        RegistrationResponse body = response.body();

                        SharedPreferences.Editor editor = getSharedPreferences("token", MODE_PRIVATE)
                                .edit();

                        editor.putString("token_type", body.getToken_type());
                        editor.putString("access_token", body.getAccess_token());
                        editor.putString("refresh_token", body.getRefresh_token());
                        editor.apply();

                        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                    } else {

                        String error = response.message().equals("Unauthorized") ? "Las credenciales proporcionadas no son validas" : "Se produjo un error, intente nuevamente";
                        ErrorService.showError(error_message, error);

                        Log.e(TAG, response.message());
                    }

                    registerButton.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {

                    Log.d(TAG, t.getLocalizedMessage());

                    ErrorService.showError(error_message, "No fue posible cominicarse con el servidor. Verifique si tiene internet.");

                    registerButton.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
