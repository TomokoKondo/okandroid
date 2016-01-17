package com.example.kondoutomoko.okandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonMaru, buttonBatu;
    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInfo = (TextView)findViewById(R.id.text_info);
        buttonMaru = (Button)findViewById(R.id.btn_maru);
        buttonBatu = (Button)findViewById(R.id.btn_batu);

        buttonMaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInfo.setText("正解");
                setButtonEnable(false);
            }
        });
        buttonBatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInfo.setText("不正解");
                setButtonEnable(false);
            }
        });
    }

    private void setButtonEnable(boolean isEnable) {
        buttonMaru.setEnabled(isEnable);
        buttonBatu.setEnabled(isEnable);
    }
}
