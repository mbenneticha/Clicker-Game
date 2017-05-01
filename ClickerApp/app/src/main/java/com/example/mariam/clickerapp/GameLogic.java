package com.example.mariam.clickerapp;


public class GameLogic {

    private int clickCount;

    public GameLogic(){

        this.clickCount = 0;
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
}
