package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

public class GameLoopActivity extends AppCompatActivity {

    private GameLoop gameLoop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.gameLoop = new GameLoop(this);


        setContentView(gameLoop);
    }

    @Override
    protected void onPause(){
        super.onPause();
        this.gameLoop.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.gameLoop.resume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return this.gameLoop.touchCheck(event);

    }
}
