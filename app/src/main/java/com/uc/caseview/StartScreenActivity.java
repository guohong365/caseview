package com.uc.caseview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartScreenActivity extends ActivityBase implements Runnable {

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private static final int MESSAGE_INIT_ERROR=1000;
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==MESSAGE_INIT_ERROR){
                showError();
                finish();
                return;
            }
            super.handleMessage(msg);
        }
    };

    private void showError() {
        Toast.makeText(this,R.string.init_error, Toast.LENGTH_SHORT);
    }

    void startMainActivity() {
        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void run() {
        try {
            mApp.init();
        } catch (IOException e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MESSAGE_INIT_ERROR);
            return;
        }
        startMainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        mHandler.postDelayed(this, AUTO_HIDE_DELAY_MILLIS);
    }

}
