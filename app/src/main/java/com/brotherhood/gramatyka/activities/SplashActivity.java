package com.brotherhood.gramatyka.activities;

import android.content.Intent;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.utils.BaseActivity;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                }
            }
        }).start();
    }
}
