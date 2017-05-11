package com.example.mariam.clickerapp;


import android.content.Context;
import android.widget.Toast;
import java.text.NumberFormat;

public class GameLogic {

    /*  GLOBAL VARIABLES
    clickedCount = total number of clicks
    level = current level
    clickValue = increment amount in $
    unlockValue = amount in $ required to unlock/reach next level/ability
    total_currency = overall earned $ amount
    current_currency = $ amount altered by spending
     */
    private int clickCount, level;
    private double clickValue, unlockValue, total_currency, current_currency;

    public GameLogic(){
        this.clickCount = 0;
        this.total_currency = 0.00;
        this.current_currency = 0.00;
        this.clickValue = 0.50;
        this.level = 0;
        this.unlockValue = 5.00;
    }


    /* CLICK LOGIC */
    // 'icc' == 'incrementClickCount'
    // Shorted here for ease of use
    public void icc(){
        this.clickCount++;
        this.total_currency += clickValue;
        this.current_currency += clickValue;
    }

    public void incClicks(int clicks){
        this.clickCount += clicks;
    }

    public int getClickCount(){
        return this.clickCount;
    }


    /*CURRENCY LOGIC */
    public double getTotalCurrency(){
        NumberFormat nf = NumberFormat.getInstance(); // get instance
        nf.setMaximumFractionDigits(2); // set decimal places
        Double tot_curr = new Double(nf.format(this.total_currency));
        return tot_curr;
    }

    public double getCurrentCurrency(){
        NumberFormat nf = NumberFormat.getInstance(); // get instance
        nf.setMaximumFractionDigits(2); // set decimal places
        Double cur_curr = new Double(nf.format(this.current_currency));
        return cur_curr;
    }

    public void incrementClickValue(double valueIncrease){
        this.clickValue += valueIncrease;
    }

    public void buyUnlockable(double unlockablePrice){
        if (current_currency >= unlockablePrice){
            this.current_currency -= unlockablePrice;
        }
        else{
            String text = "You don't have enough money in your wallet!";
           // Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }


    /* LEVEL LOGIC */
    public int getLevel(){
        return this.level;
    }

    public double getUnlockValue() { return this.unlockValue; }

    public void levelUp(){
        this.level++;
        this.unlockValue *= 3;
    }


}
