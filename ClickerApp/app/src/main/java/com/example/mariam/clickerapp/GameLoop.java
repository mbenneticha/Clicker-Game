package com.example.mariam.clickerapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class GameLoop extends SurfaceView implements Runnable {

    public GameLogic gameLogic;

    public IncButton incButton;
    public Hamster hamster;
    public Ability ability_1;
    public Ability ability_2;
    public Ability ability_3;
    public Ability ability_4;

    volatile boolean loopRunning;
    private Thread gameLoopThread = null;

    private Bitmap background;
    private Bitmap screen;
    private Bitmap cage;


    private int WIDTH;
    private int HEIGHT;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GameLoop(Context context) {
        super(context);

        this.gameLogic = new GameLogic();


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point loc = new Point();
        display.getSize(loc);
        this.WIDTH = loc.x;
        this.HEIGHT = loc.y;
        int screenWidth = this.WIDTH - 139;
        int screenHeight = (this.HEIGHT / 2) - 125;
        int cageWidth = (this.WIDTH - 298);
        int cageHeight = ((screenHeight / 4) * 3) - 24;
        int incButtonWidth = (this.WIDTH / 2);
        int incButtonHeight = (this.WIDTH / 2);

        this.background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bluetexturedbg), this.WIDTH, this.HEIGHT, false);
        this.screen = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.screen), screenWidth, screenHeight, false);
        this.cage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.hamstercage), cageWidth, cageHeight, false);


        this.incButton = new IncButton(context, incButtonWidth, incButtonHeight, this.WIDTH, this.HEIGHT);

        this.ability_1 = new Ability(context, "Peas", 1, R.id.button_ability_1, R.drawable.ability_button_generic, this.WIDTH, this.HEIGHT);
        this.ability_2 = new Ability(context, "Broccoli", 2, R.id.button_ability_2, R.drawable.ability_button_generic, this.WIDTH, this.HEIGHT);
        this.ability_3 = new Ability(context, "Carrot", 3, R.id.button_ability_3, R.drawable.ability_button_generic, this.WIDTH, this.HEIGHT);
        this.ability_4 = new Ability(context, "Yogurt Drops", 4, R.id.button_ability_4, R.drawable.ability_button_generic, this.WIDTH, this.HEIGHT);

        this.hamster = new Hamster(context, this.WIDTH, this.HEIGHT);


        surfaceHolder = getHolder();
        paint = new Paint();


    }


    @Override
    public void run(){
        while(loopRunning){
            update();
            draw();
            control();
        }
    }

    private void update(){

    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            canvas.drawBitmap(this.background, 0, 0, null);
            canvas.drawBitmap(this.screen, 75, 75, null);
            canvas.drawBitmap(this.cage, 155, 322, null);
            canvas.drawBitmap(this.incButton.getImage(), this.incButton.getXPos(), this.incButton.getYPos(), null);

            canvas.drawBitmap(this.ability_1.getAbilityImage(), this.ability_1.getXPos(), this.ability_1.getYPos(), null);
            canvas.drawBitmap(this.ability_2.getAbilityImage(), this.ability_2.getXPos(), this.ability_2.getYPos(), null);
            canvas.drawBitmap(this.ability_3.getAbilityImage(), this.ability_3.getXPos(), this.ability_3.getYPos(), null);
            canvas.drawBitmap(this.ability_4.getAbilityImage(), this.ability_4.getXPos(), this.ability_4.getYPos(), null);

            canvas.drawBitmap(this.hamster.getImage(), this.hamster.getX(), this.hamster.getY(), null);

            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText("0", ((this.WIDTH / 2) - 22), 228, paint);
            paint.setTextSize(75);
            canvas.drawText(Integer.toString(this.gameLogic.getClickCount()), ((this.WIDTH / 5) * 3), 220, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try{
            this.gameLoopThread.sleep(5);
        } catch ( InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void pause(){
        this.loopRunning = false;
        try{
            this.gameLoopThread.join();
        } catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void resume(){
        this.loopRunning = true;
        this.gameLoopThread = new Thread(this);
        this.gameLoopThread.start();
    }

    public void incClicks(int clicks){
        this.gameLogic.incClicks(clicks);
    }

    public void icc(){
        this.gameLogic.icc();
    }


    public void updateUI(){
        TextView clickCountText = (TextView) findViewById(R.id.click_number_output);
        String count = Integer.toString(this.gameLogic.getClickCount());
        clickCountText.setText(count);
    }

}
