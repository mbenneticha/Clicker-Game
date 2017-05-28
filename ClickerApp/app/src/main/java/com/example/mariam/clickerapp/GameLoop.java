package com.example.mariam.clickerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class GameLoop extends SurfaceView implements Runnable {

    public GameLogic gameLogic;

    public IncButton incButton;
    public Hamster hamster;
    public Ability ability_1;
    public Ability ability_2;
    public Ability ability_3;
    public Ability ability_4;

    public Upgrade upgrade_food;
    public Upgrade upgrade_water;
    public Upgrade upgrade_hut;
    public Upgrade upgrade_wheel;

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

        this.incButton = new IncButton(context, "IncButton", 0, R.drawable.inc_button_pressed, R.drawable.inc_button_generic, this.WIDTH, this.HEIGHT, incButtonWidth, incButtonHeight);

        this.ability_1 = new Ability(context, "Peas", 1, R.drawable.ability_peas_active_onbutton, R.drawable.ability_peas_onbutton, R.drawable.ability_peas_locked_onbutton, this.WIDTH, this.HEIGHT, 250, 250){
            @Override
            public double turnOnAbility(double passedInValue){
                this.isActive = true;
                double newClickValue = (passedInValue * 2);
                return newClickValue;
            }

            @Override
            public double turnOffAbility(double passedInValue){
                double returnValue;
                if(this.isActive) {
                    this.isActive = false;
                    returnValue = (passedInValue / 2);
                } else {
                    returnValue = passedInValue;
                }

                return returnValue;
            }
        };
        this.ability_2 = new Ability(context, "Broccoli", 2, R.drawable.ability_broccoli_active_onbutton, R.drawable.ability_broccoli_onbutton, R.drawable.ability_broccoli_locked_onbutton, this.WIDTH, this.HEIGHT, 250, 250){
            @Override
            public double turnOnAbility(double passedInValue){
                this.isActive = true;
                double newClickValue = passedInValue;
                return newClickValue;
            }

            @Override
            public double turnOffAbility(double passedInValue){
                this.isActive = false;
                double newClickValue = passedInValue;
                return newClickValue;
            }
        };
        this.ability_3 = new Ability(context, "Carrot", 3, R.drawable.ability_carrot_active_onbutton, R.drawable.ability_carrot_onbutton, R.drawable.ability_carrot_locked_onbutton, this.WIDTH, this.HEIGHT, 250, 250){
            @Override
            public double turnOnAbility(double passedInValue){
                this.isActive = true;
                double newClickValue = passedInValue;
                return newClickValue;
            }

            @Override
            public double turnOffAbility(double passedInValue){
                this.isActive = false;
                double newClickValue = passedInValue;
                return newClickValue;
            }
        };
        this.ability_4 = new Ability(context, "Yogurt Drops", 4, R.drawable.ability_yogurtdrops_active_onbutton, R.drawable.ability_yogurtdrops_onbutton, R.drawable.ability_yogurtdrops_locked_onbutton, this.WIDTH, this.HEIGHT, 250, 250){
            @Override
            public double turnOnAbility(double passedInValue){
                this.isActive = true;
                double newClickValue = passedInValue;
                return newClickValue;
            }

            @Override
            public double turnOffAbility(double passedInValue){
                this.isActive = false;
                double newClickValue = passedInValue;
                return newClickValue;
            }
        };

        this.hamster = new Hamster(context, this.WIDTH, this.HEIGHT);

        this.upgrade_food = new Upgrade(context, "Food Dish", 1, R.drawable.fooddish_basic, R.drawable.fooddish_deluxe, R.drawable.fooddish_luxury, R.drawable.fooddish_golden, R.drawable.fooddish_not_unlocked, this.WIDTH, this.HEIGHT, 200, 150);
        this.upgrade_water = new Upgrade(context, "Water Bottle", 2, R.drawable.waterbottle_basic, R.drawable.waterbottle_deluxe, R.drawable.waterbottle_luxury, R.drawable.waterbottle_golden, R.drawable.waterbottle_not_unlocked, this.WIDTH, this.HEIGHT, 200, 200);
        this.upgrade_hut = new Upgrade(context, "Hamster Hut", 3, R.drawable.hut_basic, R.drawable.hut_deluxe, R.drawable.hut_luxury, R.drawable.hut_golden, R.drawable.hut_not_unlocked, this.WIDTH, this.HEIGHT, 200, 200);
        this.upgrade_wheel = new Upgrade(context, "Hamster Wheel", 4, R.drawable.wheel_basic, R.drawable.wheel_deluxe, R.drawable.wheel_luxury, R.drawable.wheel_golden, R.drawable.wheel_not_unlocked, this.WIDTH, this.HEIGHT, 200, 200);


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
        if(this.gameLogic.getTotalCurrency() >= this.gameLogic.getUnlockValue()){
            this.gameLogic.levelUp();
        }

        if(this.gameLogic.getLevel() >= 1){
            if (!this.ability_1.isUnlocked()){
                this.ability_1.unlockAbility();
            }
            this.upgrade_food.isUnlockable = true;
        }
        if(this.gameLogic.getLevel() >= 2){
            this.upgrade_water.isUnlockable = true;
        }
        if(this.gameLogic.getLevel() >= 3){
            this.upgrade_hut.isUnlockable = true;
        }
        if(this.gameLogic.getLevel() >= 4){
            this.upgrade_wheel.isUnlockable = true;
        }

        if(this.gameLogic.getTotalCurrency() >= this.gameLogic.getUnlockValue()){
            this.gameLogic.levelUp();
        }

        if(this.gameLogic.getLevel() >= 5){
            if (!this.ability_2.isUnlocked()){
                this.ability_2.unlockAbility();
            }
        }

        if(this.gameLogic.getLevel() >= 10){
            if (!this.ability_3.isUnlocked()){
                this.ability_3.unlockAbility();
            }
        }
        if(this.gameLogic.getLevel() >= 15){
            if (!this.ability_4.isUnlocked()){
                this.ability_4.unlockAbility();
            }
        }

    }

    private void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            canvas.drawBitmap(this.background, 0, 0, null);
            canvas.drawBitmap(this.screen, 75, 75, null);
            canvas.drawBitmap(this.cage, 155, 322, null);
            canvas.drawBitmap(this.incButton.getCurrentImage(), this.incButton.getXPos(), this.incButton.getYPos(), null);

            canvas.drawBitmap(this.ability_1.getCurrentImage(), this.ability_1.getXPos(), this.ability_1.getYPos(), null);
            canvas.drawBitmap(this.ability_2.getCurrentImage(), this.ability_2.getXPos(), this.ability_2.getYPos(), null);
            canvas.drawBitmap(this.ability_3.getCurrentImage(), this.ability_3.getXPos(), this.ability_3.getYPos(), null);
            canvas.drawBitmap(this.ability_4.getCurrentImage(), this.ability_4.getXPos(), this.ability_4.getYPos(), null);

            canvas.drawBitmap(this.hamster.getImage(), this.hamster.getX(), this.hamster.getY(), null);

            canvas.drawBitmap(this.upgrade_food.getCurrentImage(), this.upgrade_food.getXPos(), this.upgrade_food.getYPos(), null);
            canvas.drawBitmap(this.upgrade_water.getCurrentImage(), this.upgrade_water.getXPos(), this.upgrade_water.getYPos(), null);
            canvas.drawBitmap(this.upgrade_hut.getCurrentImage(), this.upgrade_hut.getXPos(), this.upgrade_hut.getYPos(), null);
            canvas.drawBitmap(this.upgrade_wheel.getCurrentImage(), this.upgrade_wheel.getXPos(), this.upgrade_wheel.getYPos(), null);


            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText(Double.toString(this.gameLogic.getTotalCurrency()), ((this.WIDTH / 5)), 220, paint);
            paint.setTextSize(100);
            canvas.drawText(Integer.toString(this.gameLogic.getLevel()), ((this.WIDTH / 2) - 22), 228, paint);
            paint.setTextSize(75);
            canvas.drawText(Double.toString(this.gameLogic.getCurrentCurrency()), ((this.WIDTH / 5) * 3), 220, paint);

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

        // Save file on pause
        SharedPreferences prefs = getContext().getSharedPreferences(
                "com.example.mariam.clickerapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("lvl", gameLogic.getLevel());
        editor.putInt("clicks", gameLogic.getClickCount());
        editor.putLong("clickval", Double.doubleToRawLongBits(gameLogic.getClickValue()));
        editor.putLong("currentCurrency", Double.doubleToRawLongBits(gameLogic.getCurrentCurrency()));
        editor.putLong("totalCurrency", Double.doubleToRawLongBits(gameLogic.getTotalCurrency()));
        editor.putInt("foodlvl", gameLogic.getFoodLevel());
        editor.putInt("waterlvl", gameLogic.getWaterLevel());
        editor.putInt("hutlvl", gameLogic.getHutLevel());
        editor.putInt("wheellvl", gameLogic.getWheelLevel());
        editor.putLong("unlockValue", Double.doubleToRawLongBits(gameLogic.getUnlockValue()));
        editor.commit();
    }

    public void resume(){
        // Load file on resume
        SharedPreferences prefs = getContext().getSharedPreferences(
                "com.example.mariam.clickerapp", Context.MODE_PRIVATE);
        int defaultValue = 0;
        long defaultLong = 0;
        long defaultUnlock = 5;
        double defaultClickVal = 0.50;  // CHANGE THIS FOR TESTING
        gameLogic.setLevel(prefs.getInt("lvl", defaultValue));
        gameLogic.setClickCount(prefs.getInt("clicks", defaultValue));
        gameLogic.setClickValue(Double.longBitsToDouble(prefs.getLong("unlockValue", Double.doubleToRawLongBits(defaultClickVal))));
        gameLogic.setCurrentCurrency(Double.longBitsToDouble(prefs.getLong("currentCurrency", defaultLong)));
        gameLogic.setTotalCurrency(Double.longBitsToDouble(prefs.getLong("totalCurrency", defaultLong)));
        gameLogic.setFoodLevel(prefs.getInt("foodlvl", defaultValue));
        gameLogic.setWaterLevel(prefs.getInt("waterlvl", defaultValue));
        gameLogic.setHutLevel(prefs.getInt("hutlvl", defaultValue));
        gameLogic.setWheelLevel(prefs.getInt("wheellvl", defaultValue));
        gameLogic.setUnlockValue(Double.longBitsToDouble(prefs.getLong("unlockValue", defaultUnlock)));

        this.loopRunning = true;
        this.gameLoopThread = new Thread(this);
        this.gameLoopThread.start();
    }

    public void incClicks(int clicks){ this.gameLogic.incClicks(clicks); }

    public void icc(){
        this.gameLogic.icc();
    }


    public void updateUI(){
        TextView clickCountText = (TextView) findViewById(R.id.click_number_output);
        String count = Integer.toString(this.gameLogic.getClickCount());
        clickCountText.setText(count);
    }


    public boolean touchCheck(MotionEvent event){

        IncButton incButton = this.incButton;

        Ability ability_1 = this.ability_1;
        Ability ability_2 = this.ability_2;
        Ability ability_3 = this.ability_3;
        Ability ability_4 = this.ability_4;

        Upgrade upgrade_food = this.upgrade_food;
        Upgrade upgrade_water = this.upgrade_water;
        Upgrade upgrade_hut = this.upgrade_hut;
        Upgrade upgrade_wheel = this.upgrade_wheel;

        if(this.gameLogic.handleClick(event, this.incButton)){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                this.icc();
            }
            return true;
        } else if(this.ability_1.isUnlocked() && this.gameLogic.handleClick(event, this.ability_1)){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                this.gameLogic.handleAbility1(this.ability_1);
            }
            return true;
        } else if(this.ability_2.isUnlocked() && this.gameLogic.handleClick(event, this.ability_2)){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                this.gameLogic.handleAbility2(this.ability_2);
            }
            return true;
        } else if(this.ability_3.isUnlocked() && this.gameLogic.handleClick(event, this.ability_3)){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                this.gameLogic.handleAbility3(this.ability_3);
            }
            return true;
        } else if(this.ability_4.isUnlocked() && this.gameLogic.handleClick(event, this.ability_4)){
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                this.gameLogic.handleAbility4(this.ability_4);
            }
            return true;
        } else if(this.upgrade_food.isUnlockable() && this.gameLogic.handleClick(event, this.upgrade_food)){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                this.gameLogic.upgradeFood(this.upgrade_food);
            }
            return true;
        }else if(this.upgrade_water.isUnlockable() && this.gameLogic.handleClick(event, this.upgrade_water)){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                this.gameLogic.upgradeWater(this.upgrade_water);
            }
            return true;
        }else if(this.upgrade_hut.isUnlockable() && this.gameLogic.handleClick(event, this.upgrade_hut)){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                this.gameLogic.upgradeHut(this.upgrade_hut);
            }
            return true;
        }else if(this.upgrade_wheel.isUnlockable() && this.gameLogic.handleClick(event, this.upgrade_wheel)){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                this.gameLogic.upgradeWheel(this.upgrade_wheel);
            }
            return true;
        }

        return false;
    }

    public void turnAbilityOff() {
        Log.d("ABILITY", "In 'turnAbilityOff");
        double newClickValue = this.ability_1.turnOffAbility(this.gameLogic.getClickValue());
        this.gameLogic.setClickValue(newClickValue);
    }

}
