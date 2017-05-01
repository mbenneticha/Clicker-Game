package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class Ability {

    private String abilityName;
    private int abilityId;
    private boolean isUnlocked;
    private int abilityButtonId;
    private Bitmap abilityImage;
    private int width;
    private int height;
    private int xPos;
    private int yPos;

    public Ability(Context context, String abilityName, int abilityId, int abilityButtonId, int abilityImage, int canvasWidth, int canvasHeight){
        this.abilityName = abilityName;
        this.abilityId = abilityId;
        this.isUnlocked = false;
        this.abilityButtonId = abilityButtonId;
        this.abilityImage = BitmapFactory.decodeResource(context.getResources(), abilityImage);;
        this.width = this.abilityImage.getWidth();
        this.height = this.abilityImage.getHeight();
        this.xPos = (((canvasWidth / 4) * this.abilityId) - 42) - (this.width);
        this.yPos = ((canvasHeight / 2) - 16);
        if((this.abilityId == 1) || (this.abilityId == 4)){
            this.yPos += 125;
        }
    }

    public Ability(String abilityName, int abilityId){
        this.abilityName = abilityName;
        this.abilityId = abilityId;
        this.isUnlocked = false;
        this.abilityButtonId = -1;
        this.abilityImage = null;
        this.width = 0;
        this.height = 0;
    }

    public int getXPos(){
        return this.xPos;
    }

    public int getYPos(){
        return this.yPos;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }
    public boolean isAbilityUnlocked(){
        return this.isUnlocked;
    }

    public void unlockAbility(){
        this.isUnlocked = true;
    }

    public void displayAbilityToast(Context context) {
        String text = "Ability #" + Integer.toString(abilityId) + " clicked!";
        Toast abilityToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        abilityToast.show();
    }

    public void execute(Context context){
        this.displayAbilityToast(context);
    }

    public String getAbilityName(){
        return this.abilityName;
    }

    public int getAbilityId(){
        return this.abilityId;
    }

    public boolean isUnlocked(){
        return this.isUnlocked;
    }

    public int getAbilityButtonId(){
        return this.abilityButtonId;
    }

    public Bitmap getAbilityImage(){
        return this.abilityImage;
    }

}
