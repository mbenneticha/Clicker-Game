package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mariam on 5/26/17.
 */

public class Upgrade extends Button{

    public boolean isUnlockable;
    public int upgradeLevel;
    private boolean isUnlocked;
    private Bitmap image_Basic, image_Deluxe, image_Luxury, image_Gold, image_Locked, image_purchase;

    public Upgrade(Context context, String upgradeName, int upgradeId, int image_Basic, int image_Deluxe, int image_Luxury, int image_Gold, int image_Locked, int image_purchase_background, int canvasWidth, int canvasHeight, int width, int height) {
        super(context, upgradeName, upgradeId, image_Basic, image_Locked, width, height);
        this.isUnlockable = false;
        this.isUnlocked = false;
        this.upgradeLevel = 0;
        this.image_Basic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Basic), width, height, false);
        this.image_Deluxe = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Deluxe), width, height, false);
        this.image_Luxury = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Luxury), width, height, false);
        this.image_Gold = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Gold), width, height, false);
        this.image_Locked = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Locked), width, height, false);
        this.image_purchase = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_purchase_background), width, height, false);
        int xPos = 0, yPos = 0;

        //food dish
        if (upgradeId == 1){
            xPos = ((canvasWidth / 8) * 6) - (width / 2) - 20;
            yPos = (canvasHeight / 8) + height + 400;
        }
        //water bottle
        if (upgradeId == 2){
            xPos = ((canvasWidth / 8) * 6) - (width / 2) - 20;
            yPos = (canvasHeight / 8) + height + 10;
        }
        //hut
        if (upgradeId == 3){
            xPos = ((canvasWidth / 8) * 2) - (width / 2) + 40;
            yPos = (canvasHeight / 8) + height + 325;
        }
        //wheel
        if (upgradeId == 4){
            xPos = ((canvasWidth / 8) * 4) - (width / 2);
            yPos = (canvasHeight / 8) + height - 20;
        }

        this.setXPos(xPos);
        this.setYPos(yPos);
        this.setBounds();
        this.setImageLocked();

    }


    public void setImageLocked(){this.setCurrentImage(this.image_Locked);}
    public void setImageBasic(){this.setCurrentImage(this.image_Basic);}
    public void setImageDeluxe(){this.setCurrentImage(this.image_Deluxe);}
    public void setImageLuxury(){this.setCurrentImage(this.image_Luxury);}
    public void setImageGold(){this.setCurrentImage(this.image_Gold);}


    public boolean isUnlockable() {
        return this.isUnlockable;
    }

    public boolean isUnlocked(){return this.isUnlocked;}

    public void setIsUnlocked(boolean status) { this.isUnlocked = status; }

    public int getUpgradeLevel(){return this.upgradeLevel;}
    public void setUpgradeLevel(int value) { this.upgradeLevel = value; }
    public void incrementUpgradeLevel(){ this.upgradeLevel ++;}


    //unlock_number corresponds to:
    // 0 = locked
    // 1 = basic
    // 2 = deluxe
    // 3 = luxury
    // 4 = gold
    public void unlockUpgrade(int unlock_number){

        if (unlock_number == 1 && this.isUnlockable()){
            this.setImageBasic();
            this.isUnlocked = true;
        }
        else if (unlock_number == 2 && this.isUnlockable()){
            this.setImageDeluxe();
            this.isUnlocked = true;
        }
        else if (unlock_number == 3 && this.isUnlockable()){
            this.setImageLuxury();
            this.isUnlocked = true;
        }
        else if (unlock_number == 4 && this.isUnlockable()){
            this.setImageGold();
            this.isUnlocked = true;
        }
        else{
            this.setImageLocked();
            this.isUnlocked = false;
        }
    }

    public void resetImage(int unlock_number) {
        if (unlock_number == 1){
            this.setImageBasic();
        }
        else if (unlock_number == 2){
            this.setImageDeluxe();
        }
        else if (unlock_number == 3){
            this.setImageLuxury();
        }
        else if (unlock_number == 4){
            this.setImageGold();
        }
        else{
            this.setImageLocked();
        }
    }

    public void displayUpgradeToast(Context context) {
        String text = "Upgrade #" + Integer.toString(this.getId()) + " clicked!";
        Toast upgradeToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        upgradeToast.show();
    }


}
