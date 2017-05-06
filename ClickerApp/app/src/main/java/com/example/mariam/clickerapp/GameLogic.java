package com.example.mariam.clickerapp;


public class GameLogic {

    /*
    clickedCount = total number of clicks
    level = current level
    clickValue = increment amount (applied with abilities or AFK
    unlockValue = amount required to unlock/reach next level
     */
    private int clickCount, level;
    private float unlockValue;
    //private float clickValue;

    public GameLogic(){
        this.clickCount = 0;
        this.level = 0;
        //this.clickValue = 1;
        this.unlockValue = 5;
    }

    // 'icc' == 'incrementClickCount'
    // Shorted here for ease of use
    public void icc(){
        this.clickCount++;
    }

    public void incClicks(int clicks){
        this.clickCount += clicks;
    }

    public int getClickCount(){
        return this.clickCount;
    }

    public int getLevel(){
        return this.level;
    }

    public float getUnlockValue() { return this.unlockValue; }

    public void levelUp(){
        this.level++;
        this.unlockValue *= 3;
    }


}
