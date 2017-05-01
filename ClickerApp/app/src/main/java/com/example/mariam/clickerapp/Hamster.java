package com.example.mariam.clickerapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Hamster {

    final private int width = 200;
    final private int height = 200;

    private int posX;
    private int posY;
    private Bitmap hamsterImage_default;
    private Bitmap hamsterImage_default2;
    private Bitmap hamsterImage_eat;
    private Bitmap hamsterImage_eat2;


    public Hamster(Context context, int canvasWidth, int canvasHeight){
        this.posX = (canvasWidth / 2) - (this.width / 2);
        this.posY = (canvasHeight / 4) + (this.height);
        this.hamsterImage_default = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_default), this.width, this.height, false);
        this.hamsterImage_default2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_default3);
        this.hamsterImage_eat = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_eat);
        this.hamsterImage_eat2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_eat3);
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public int getX(){
        return this.posX;
    }

    public int getY(){
        return this.posY;
    }

    public Bitmap getImage(){
        return this.hamsterImage_default;
    }

}
