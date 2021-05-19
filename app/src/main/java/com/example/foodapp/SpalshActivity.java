package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SpalshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        ////status bar hide start////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///status bar hide end///

        ///add hander start///

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpalshActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

        ///add hander end///
    }
}