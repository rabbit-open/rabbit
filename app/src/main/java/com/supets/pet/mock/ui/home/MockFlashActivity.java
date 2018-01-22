package com.supets.pet.mock.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.supets.pet.mockui.R;


public class MockFlashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startHome();
            }
        }, 2000);
    }

    private void startHome() {
        Intent intent = new Intent(this, MockUiActivity.class);
        startActivity(intent);
        finish();
    }
}
