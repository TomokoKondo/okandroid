package com.example.kondoutomoko.okandroid;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Runnable {

    private Button buttonMaru, buttonBatu;
    private TextView textInfo, textQuestion;

    private Thread thread;
    private boolean isRunning = false;
    private Handler handler;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textQuestion = (TextView)findViewById(R.id.text_question);
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
        thread = new Thread(this);
        isRunning = true;
        thread.start();
        handler = new Handler();
    }
    @Override
    protected void onDestroy() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            long nextTime = System.currentTimeMillis() + 2000;

            handler.post(new Runnable() {
                @Override
                public void run() {
                    counter++;
                    if(counter==2){
                    textQuestion.setText("Mondai2");
                    textInfo.setText("cotae");
                   }
                }
            });

            try {
                Thread.sleep(nextTime - System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setButtonEnable(boolean isEnable) {
        buttonMaru.setEnabled(isEnable);
        buttonBatu.setEnabled(isEnable);
    }
}
