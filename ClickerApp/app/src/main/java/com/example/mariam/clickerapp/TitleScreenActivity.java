package com.example.mariam.clickerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class TitleScreenActivity extends AppCompatActivity {

    private TitleScreen titleScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.titleScreen = new TitleScreen(this);


        setContentView(titleScreen);
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.titleScreen.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.titleScreen.resume();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        Bundle bundle = new Bundle();
        bundle.putBoolean("startGame", true);

        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
        return true;

    }

}
