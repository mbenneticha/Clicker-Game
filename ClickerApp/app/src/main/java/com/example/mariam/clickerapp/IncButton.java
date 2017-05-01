package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.Toast;


public class IncButton {

    private int id;
    private int width;
    private int height;
    private int xPos;
    private int yPos;
    public Bitmap image;
    private Rect bounds;

    public IncButton(Context context, int incButtonWidth, int incButtonHeight, int canvasWidth, int canvasHeight){

        this.width = (canvasWidth / 2);
        this.height = (canvasWidth / 2);
        this.xPos = (canvasWidth / 4);
        this.yPos = (((canvasHeight / 3) * 2) - 50);

        this.image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.inc_button_generic), incButtonWidth, incButtonHeight, false);

        this.bounds = new Rect(this.xPos, this.yPos, (this.xPos + this.height), (this.yPos + this.width));
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

    public Bitmap getImage(){
        return this.image;
    }

    public Rect getBounds(){
        return this.bounds;
    }

    public void displayAbilityToast(Context context) {
        String text = "IncButton clicked!";
        Toast abilityToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        abilityToast.show();
    }

    public void clickDown(Context context){
        this.image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.inc_button_pressed), this.width, this.height, false);
    }

    public void clickUp(Context context){
        this.image = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.inc_button_generic), this.width, this.height, false);
    }


}

