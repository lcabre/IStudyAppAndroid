package ar.com.hipnos.leo.istudy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ar.com.hipnos.leo.istudy.api.ApiService;
import ar.com.hipnos.leo.istudy.api.ErrorService;
import ar.com.hipnos.leo.istudy.api.modell.Materia;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MateriaActivity extends AppCompatActivity {

    private static final String TAG = MateriaActivity.class.getSimpleName();
    private Context context = this;

    @BindView(R.id.reveal_items)
    LinearLayout settings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nombre)
    TextView nombre;

    @BindView(R.id.imagen)
    SimpleDraweeView imagen;

    @BindView(R.id.codigo)
    TextView codigo;

    @BindView(R.id.correlativas)
    TextView correlativas;

    Boolean visible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Materia materia = (Materia) getIntent().getSerializableExtra("materia");

        nombre.setText(materia.getNombre());
        codigo.setText(materia.getCodigo().toString());

        if(materia.getCorrelativas() != null){

            JSONObject jObject;
            JSONArray jArray = null;
            String correl = "";

            try {
                jObject = new JSONObject(materia.getCorrelativas());
                jArray = jObject.getJSONArray("codigos");
                for (int i=0; i < jArray.length(); i++)
                {
                    try {
                        String cod = jArray.getString(i);
                        if(i != jArray.length()-1)
                            correl = correl.concat(cod+", ");
                        else
                            correl = correl.concat(cod);

                    } catch (JSONException e) {
                        // Oops
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            correlativas.setText(correl);
        }
        else
            correlativas.setText("");


        Uri uri = Uri.parse(materia.getImagen());
        imagen.setImageURI(uri);

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

                ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
                TransitionManager.beginDelayedTransition(view, set);

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

        Intent i = new Intent(MateriaActivity.this, MainActivity.class);
        startActivity(i);
    }
}
