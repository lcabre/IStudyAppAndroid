package ar.com.hipnos.leo.istudy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        TextView tx = (TextView)findViewById(R.id.titulo);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LeagueSpartan-Bold.otf");
        tx.setTypeface(custom_font);
    }
}
