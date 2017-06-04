package com.example.mariam.clickerapp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Ability extends Button{

    private boolean isUnlocked;
    private int rechargeTime;
    private Bitmap image_Locked;
    public Bitmap recharging;
    public RectF outerBounds;
    public RectF innerBounds;
    public Paint chargingPaint;
    public Paint erasePaint;
    public ValueAnimator chargeAnimation;
    public float thickness;
    public float sweepAngle;

    public boolean isActive;
    public boolean isRecharging;
    public String displayText;

    public Ability(Context context, String abilityName, int abilityId, int image_Active, int image_Rest, int image_Locked, int canvasWidth, int canvasHeight, int width, int height, int rechargeTime, String displayText){
        super(context, abilityName, abilityId, image_Active, image_Rest, width, height);
        this.displayText = displayText;
        this.rechargeTime = rechargeTime;
        this.isActive = false;
        this.isUnlocked = false;
        this.isRecharging = false;
        this.image_Locked = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Locked), width, height, false);
        this.recharging = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


        int xPos = (((canvasWidth / 4) * abilityId) - 60) - (width);
        int yPos = ((canvasHeight / 2));
        if((abilityId == 1) || (abilityId == 4)){
            yPos += 125;
        }
        this.setXPos(xPos);
        this.setYPos(yPos);
        this.setBounds();
        this.setImageLocked();
        this.setupCharging();
    }

    public void setupCharging(){

        this.chargingPaint = new Paint();
        chargingPaint.setAntiAlias(true);
        chargingPaint.setColor(Color.parseColor("#FE9847"));

        this.erasePaint = new Paint();
        erasePaint.setAntiAlias(true);
        erasePaint.setColor(Color.TRANSPARENT);
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        this.thickness = this.getWidth() * .15f;

        this.outerBounds = new RectF(this.getXPos(), this.getYPos(), (this.getXPos() + this.getWidth()), (this.getYPos() + this.getHeight()));
        this.innerBounds = new RectF( this.getXPos() + this.thickness, this.getYPos() + this.thickness, (this.getXPos() + this.getWidth()) - this.thickness, (this.getYPos() + this.getHeight()) - this.thickness);
        this.sweepAngle = 0f;
    }

    public void setToRecharging(){
        this.setImageRecharging();
        this.isRecharging = true;
        this.isUnlocked = false;

        Timer ability1_rechargeTimer = new Timer();

        TimerTask reActivated = new TimerTask(){
            public void run() {
                Ability.this.isRecharging = false;
                Ability.this.setToCharged();
            }
        };

        ability1_rechargeTimer.schedule(reActivated, this.rechargeTime);

    }

    public void setToCharged(){
        this.isRecharging = false;
        this.isUnlocked = true;
        this.unlockAbility();
    }

    public void setImageLocked(){this.setCurrentImage(this.image_Locked);}

    public void setImageRecharging() {
        this.setCurrentImage(this.recharging);
        this.chargeAnimation = ValueAnimator.ofFloat(0f, 1f);
        this.chargeAnimation.setDuration(TimeUnit.SECONDS.toMillis(this.rechargeTime));
        this.chargeAnimation.setInterpolator(new LinearInterpolator());
        this.chargeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate (ValueAnimator animation){
                drawProgress((float) animation.getAnimatedValue());
            }
        });
        this.chargeAnimation.start();
    }

    private void drawProgress(float progress){
        this.sweepAngle = 360 * (progress * 1000);
    }

    public void unlockAbility(){
        this.isUnlocked = true;
        this.setImageAtRest();
    }

    public void displayAbilityToast(Context context) {
        String text = "Your hamster ate " + this.getName() + "!\r\n" + this.displayText;
        Toast abilityToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        abilityToast.show();
    }

    public double turnOnAbility(double passedInValue){
        this.isActive = true;
        return passedInValue;
    }

    public double turnOffAbility(double clickValue){
        double returnValue;
        if(this.isActive) {
            this.isActive = false;
            returnValue = (clickValue / 2);
        } else {
            returnValue = clickValue;
        }

        return returnValue;
    }

    public boolean isUnlocked(){
        return this.isUnlocked;
    }

    public void clickDown(Context context, double valueToAffect){
        super.clickDown();
        this.turnOnAbility(valueToAffect);

    }

    public void clickUp(Context context, double valueToAffect){
        super.clickUp();
        this.turnOffAbility(valueToAffect);
    }

}
