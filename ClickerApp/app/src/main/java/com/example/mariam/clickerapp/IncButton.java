package com.example.mariam.clickerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.Toast;


public class IncButton extends Button{

    public IncButton(Context context, String name, int id, int image_Active, int image_Rest, int canvasWidth, int canvasHeight, int width, int height){
        super(context, name, id, image_Active, image_Rest, width, height);

        int xPos = (canvasWidth / 4);
        int yPos = (((canvasHeight / 3) * 2) - 50);
        this.setXPos(xPos);
        this.setYPos(yPos);
        this.setBounds();
    }

    public void displayAbilityToast(Context context) {
        String text = "IncButton clicked!";
        Toast abilityToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        abilityToast.show();
    }

    public void clickDown(Context context){
        super.clickDown();
    }

    public void clickUp(Context context){
        super.clickDown();
    }


}

