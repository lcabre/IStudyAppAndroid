package ar.com.hipnos.leo.istudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences("token", MODE_PRIVATE);
        String hasToken = prefs.getString("access_token", null);

        final Intent i;

        if(hasToken != null) {
            i = new Intent(MainActivity.this, HomeActivity.class);
        } else {
            i = new Intent(MainActivity.this, LoginActivity.class);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(i);
            }
        },1000);
    }
}
