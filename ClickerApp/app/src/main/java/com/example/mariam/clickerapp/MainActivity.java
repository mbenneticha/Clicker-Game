package com.example.mariam.clickerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private boolean onTitle;
    protected static MediaPlayer song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        song = MediaPlayer.create(MainActivity.this,R.raw.hamsong);
        song.start();
        song.setLooping(true);
        this.onTitle = true;

        SharedPreferences prefs = this.getSharedPreferences(
                "com.example.mariam.clickerapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if(!prefs.contains("initialized")) {
            startActivityForResult(new Intent(this, TitleScreenActivity.class), 1);
        } else {
            this.startGame();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        this.startGame();
        switch(requestCode){
            case (1): {
                if (resultCode == Activity.RESULT_OK) {
                   this.startGame();
                }
                break;
            }
        }
    }


    public void startGame(){
        startActivity(new Intent(this, GameLoopActivity.class));
    }





    //for testing
    private int clickedCount;
    public int setClickedCount(int value){
        clickedCount = value;
        return clickedCount;
    }

    //for testing
    public int getClickedCount(){
        return clickedCount;
    }




}
