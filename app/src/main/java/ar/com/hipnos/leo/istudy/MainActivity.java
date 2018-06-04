package ar.com.hipnos.leo.istudy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.usuario)
    EditText usuario;

    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.register)
    public void onClick(){

        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
       //i.putExtra("variable","valor");// si quiero parsar datos a traves del intent
        startActivity(i);
    }
}
