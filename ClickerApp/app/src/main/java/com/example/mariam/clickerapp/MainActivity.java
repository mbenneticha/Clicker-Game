package com.example.mariam.clickerapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void startGame(View view){
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
