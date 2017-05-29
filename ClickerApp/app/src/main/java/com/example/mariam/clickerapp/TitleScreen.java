package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class TitleScreen extends SurfaceView implements Runnable{

    volatile boolean loopRunning;
    private Thread titleScreenThread = null;

    private int WIDTH;
    private int HEIGHT;

    private Bitmap background;
    private Bitmap hamster;
    private Bitmap option1Background;
    private Bitmap option2Background;
    private Bitmap option3Background;
    private Bitmap titleBackground;
    private Bitmap hamsterBackground;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Typeface sueEllenTypeface;

    AnimationDrawable hamsterIdle;

    public TitleScreen(Context context){
        super(context);

        loopRunning = true;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point loc = new Point();
        display.getSize(loc);
        this.WIDTH = loc.x;
        this.HEIGHT = loc.y;

        this.background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bluetexturedbg), this.WIDTH, this.HEIGHT, false);
        this.option1Background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ts_optionbackground), 600, 180, false);
        this.option2Background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ts_optionbackground), 600, 180, false);
        this.option3Background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ts_optionbackground), 600, 180, false);
        this.titleBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ts_titlebackground), this.WIDTH, (this.HEIGHT / 2), false);
        this.hamsterBackground = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ts_hamsterbackground), (this.WIDTH / 2), this.HEIGHT, false);
        this.hamster = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_default), 550, 550, false);



        surfaceHolder = getHolder();
        paint = new Paint();
        sueEllenTypeface = new TypeFactory(context).sueEllenRegular;

    }

    @Override
    public void run(){
        while(loopRunning){
            update();
            draw();
            control();
        }
    }

    public void update(){

    }

    public void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(this.background, 0, 0, null);
            canvas.drawBitmap(this.hamsterBackground, 75, 0, null);
            canvas.drawBitmap(this.titleBackground, 0, 0, null);
            canvas.drawBitmap(this.option1Background, 850, 1200, null);
            canvas.drawBitmap(this.option2Background, 850, 1500, null);
            canvas.drawBitmap(this.option3Background, 850, 1800, null);
            canvas.drawBitmap(this.hamster, 160, 1275, null);

            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            paint.setTextSize(300);
            paint.setTypeface(this.sueEllenTypeface);
            canvas.drawText("Hamster Clicker", 75, 550, paint);

            paint.setTextSize(125);
            canvas.drawText("New Game", 975, 1350, paint);
//            canvas.drawText("Load Game", 975, 1650, paint);
//            canvas.drawText("Options", 975, 1950, paint);



            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }


    private void control(){
        try{
            this.titleScreenThread.sleep(5);
        } catch ( InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void pause(){
        this.loopRunning = false;
        try{
            this.titleScreenThread.join();
        } catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void resume(){
        this.loopRunning = true;
        this.titleScreenThread = new Thread(this);
        this.titleScreenThread.start();
    }

}
