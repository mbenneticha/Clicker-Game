package com.example.mariam.clickerapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;

public class Hamster {

    final private int width = 160;
    final private int height = 160;

    private int posX;
    private int posY;
    private Bitmap hamsterImage_default;
    public  Bitmap animation_hamster_atrest;
    public  Bitmap animation_hamster_eat;
    private Bitmap hamsterImage_eat2;
    private Bitmap currentAnimation;

    public int frameWidth;
    public int frameHeight;
    public int frameCount;
    public int currentFrame;
    public int frameTimeLength;
    public Rect frameToDraw;
    public RectF whereToDraw;



    public Hamster(Context context, int canvasWidth, int canvasHeight){
        this.posX = (canvasWidth / 2) - (this.width / 2);
        this.posY = (canvasHeight / 4) + (this.height) + 50;
        this.frameWidth = 180;
        this.frameHeight = 180;
        this.frameCount = 6;
        this.currentFrame = 0;
        this.frameTimeLength = 200;

        this.hamsterImage_default = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_default), this.width, this.height, false);
        this.animation_hamster_atrest = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet_hamster_atrest), this.frameWidth * this.frameCount, this.frameHeight, false);
        this.animation_hamster_eat = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spritesheet_hamster_eating), this.frameWidth * this.frameCount, this.frameHeight, false);
        this.hamsterImage_eat2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamster_eat3);

        this.currentAnimation = this.animation_hamster_atrest;
        this.frameToDraw = new Rect(0, 0, this.width, this.height);
        this.whereToDraw = new RectF(this.posX, this.posY, this.posX + this.frameWidth, this.frameHeight);
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
        return this.currentAnimation;
    }

    public void setImage(Bitmap newImage){
        this.currentAnimation = newImage;
    }

    public void updateWhereToDraw(){
        this.whereToDraw.set((int) this.posX, this.posY, (int) this.posX + this.frameWidth, this.posY + this.frameHeight);
    }
}
