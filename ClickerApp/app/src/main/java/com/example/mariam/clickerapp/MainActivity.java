package com.example.mariam.clickerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    private boolean onTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer song = MediaPlayer.create(MainActivity.this,R.raw.hamsong);
        song.start();
        song.setLooping(true);
        this.onTitle = true;
        startActivityForResult(new Intent(this, TitleScreenActivity.class), 1);

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
