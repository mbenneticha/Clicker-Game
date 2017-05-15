package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class Ability extends Button{

    private boolean isUnlocked;
    private Bitmap image_Locked;
    public boolean isActive;

    public Ability(Context context, String abilityName, int abilityId, int image_Active, int image_Rest, int image_Locked, int canvasWidth, int canvasHeight, int width, int height){
        super(context, abilityName, abilityId, image_Active, image_Rest, width, height);
        this.isActive = false;
        this.isUnlocked = false;
        this.image_Locked = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Locked), width, height, false);

        int xPos = (((canvasWidth / 4) * abilityId) - 60) - (width);
        int yPos = ((canvasHeight / 2));
        if((abilityId == 1) || (abilityId == 4)){
            yPos += 125;
        }
        this.setXPos(xPos);
        this.setYPos(yPos);
        this.setBounds();
        this.setImageLocked();
    }

    public void setImageLocked(){this.setCurrentImage(this.image_Locked);}

    public void unlockAbility(){
        this.isUnlocked = true;
        this.setImageAtRest();
    }

    public void displayAbilityToast(Context context) {
        String text = "Ability #" + Integer.toString(this.getId()) + " clicked!";
        Toast abilityToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
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
