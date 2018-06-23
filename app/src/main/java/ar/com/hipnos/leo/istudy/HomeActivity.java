package ar.com.hipnos.leo.istudy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.view.animation.FastOutLinearInInterpolator;
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
import android.content.Intent;

import ar.com.hipnos.leo.istudy.api.ApiService;
import ar.com.hipnos.leo.istudy.api.ErrorService;
import ar.com.hipnos.leo.istudy.api.modell.Carrera;
import ar.com.hipnos.leo.istudy.api.response.LoginResponse;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.reveal_items)
    LinearLayout settings;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.home)
    RelativeLayout home;

    @BindView(R.id.error_message)
    TextView error_message;

    @BindView(R.id.lista)
    ListView lista;

    Boolean visible = false;

    String[] menuOptions = {"Carreras","Materias","Parciales"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_listview, menuOptions);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

                final Intent i;

                Context from = HomeActivity.this;
                Class to = HomeActivity.class;

                Log.i(TAG, selectedItem);

                switch (selectedItem){
                    case "Carreras":
                        to = CarrerasActivity.class;
                }

                i = new Intent(from, to);
                startActivity(i);
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
                        .addTransition(new Slide(Gravity.END))
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

        android.content.SharedPreferences prefs = getSharedPreferences("token", MODE_PRIVATE);
        prefs.edit().remove("access_token").remove("refresh_token").apply();

        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(i);
    }
}
