package ar.com.hipnos.leo.istudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.List;

import ar.com.hipnos.leo.istudy.api.ApiService;
import ar.com.hipnos.leo.istudy.api.ErrorService;
import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CarrerasActivity extends AppCompatActivity {

    private static final String TAG = CarrerasActivity.class.getSimpleName();

    @BindView(R.id.reveal_items)
    LinearLayout settings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.home)
    RelativeLayout home;

    @BindView(R.id.error_message)
    TextView error_message;

    @BindView(R.id.carreras)
    RecyclerView lista;

    @BindView(R.id.progressBar)
    ProgressBar loading;

    Boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carreras);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("token", MODE_PRIVATE);
        String accessToken = prefs.getString("access_token", null);
        String tokenType = prefs.getString("token_type", null);
        String authorization = tokenType+" "+accessToken;

        lista.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        lista.setLayoutManager(llm);

        loading.setVisibility(View.VISIBLE);

        ApiService.getCarreras( authorization, new Callback<List<Carrera>>() {
            @Override
            public void onResponse(Call<List<Carrera>> call, Response<List<Carrera>> response) {
                if (response.isSuccessful()){

                    List<Carrera> carreras = response.body();

                    CarreraAdapter adapter = new CarreraAdapter(carreras);
                    lista.setAdapter(adapter);

                    loading.setVisibility(View.GONE);

                    Log.i(TAG, response.message());
                }else{

                    String error = response.message().equals("Unauthenticated")?"No esta autenticado":"Se produjo un error, intente nuevamente";
                    ErrorService.showError(error_message, error );

                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Carrera>> call, Throwable t) {

                Log.d(TAG,t.getLocalizedMessage());

                ErrorService.showError(error_message, "No fue posible cominicarse con el servidor. Verifique si tiene internet." );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:

                TransitionSet set = new TransitionSet()
                        .addTransition(new Slide(Gravity.RIGHT))
                        .addTransition(new Fade())
                        .setDuration(500)
                        .setInterpolator(visible ? new FastOutSlowInInterpolator() :
                                new LinearOutSlowInInterpolator());

                TransitionManager.beginDelayedTransition(home, set);

                visible = !visible;
                settings.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @OnClick(R.id.logout)
    public void onClick(){

        SharedPreferences prefs = getSharedPreferences("token", MODE_PRIVATE);
        prefs.edit().remove("access_token").remove("refresh_token").apply();

        Intent i = new Intent(CarrerasActivity.this, MainActivity.class);
        startActivity(i);
    }
}
