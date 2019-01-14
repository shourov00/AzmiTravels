package com.projectidea.shourov.azmitravels;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(() -> {
            doWait();
            moveToMainScreen();
        });
        thread.start();
    }

    private void doWait() {
        for (int i = 20; i <= 60; i = i + 20) { //3s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveToMainScreen() {
        startActivity(new Intent(SplashScreenActivity.this,GetStartedActivity.class));
        finish();
    }
}
