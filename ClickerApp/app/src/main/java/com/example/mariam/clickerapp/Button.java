package com.example.mariam.clickerapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Button {

    private String name;
    private int id;
    private Bitmap image_Rest;
    private Bitmap image_Active;
    private Bitmap currentImage;
    private Rect bounds;
    private int width;
    private int height;
    private int xPos;
    private int yPos;

    public Button(Context context, String name, int id, int image_Active, int image_Rest, int width, int height ){
        this.name = name;
        this.id = id;
        this.width = width;
        this.height = height;
        this.image_Rest = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Rest), width, height, false);
        this.image_Active = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), image_Active), width, height, false);
        this.currentImage = this.image_Rest;

    }

    public void setWidth(int width){ this.width = width; }

    public void setHeight(int height){ this.height = height; }

    public void setBounds() {
        this.bounds = new Rect(this.xPos, this.yPos, (this.xPos + this.width), (this.yPos + this.height + 75));
    }

    public void setXPos(int xPos){ this.xPos = xPos; }

    public void setYPos(int yPos){ this.yPos = yPos; }

    public void setImageAtRest(){this.currentImage = this.image_Rest;}

    public void setImageActive(){this.currentImage = this.image_Active;}

    public void setCurrentImage(Bitmap image){ this.currentImage = image;}

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

    public Bitmap getCurrentImage(){
        return this.currentImage;
    }

    public Rect getBounds(){
        return this.bounds;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public void clickDown(){
        this.currentImage = this.image_Active;
    }

    public void clickUp(){
        this.currentImage = this.image_Rest;
    }






}
