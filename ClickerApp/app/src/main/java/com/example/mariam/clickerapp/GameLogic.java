package com.example.mariam.clickerapp;


import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

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

    public void setClickValue(double value){
        this.clickValue = value;
    }

    public double getClickValue(){
        return this.clickValue;
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




    public boolean handleClick(MotionEvent event, Button ability) {


        int xPos = (int) event.getX();
        int yPos = (int) event.getY();
        Rect rect = ability.getBounds();

        if (rect.contains(xPos, yPos) &&
                event.getAction() == MotionEvent.ACTION_DOWN) {

            ability.clickDown();
            return true;
        }
        if (rect.contains(xPos, yPos) &&
                event.getAction() == MotionEvent.ACTION_UP) {

            ability.clickUp();
            return true;
        }

        return false;
    }

    public void handleAbility1(final Ability ability1){
        this.clickValue = ability1.turnOnAbility(this.clickValue);

        Timer ability1_Timer = new Timer();

        TimerTask turnOff = new TimerTask(){
            public void run() {
                GameLogic.this.setClickValue(ability1.turnOffAbility(GameLogic.this.getClickValue()));
            }
        };

        ability1_Timer.schedule(turnOff, 3000);
    }

    public void handleAbility2(final Ability ability2){
        this.clickValue = ability2.turnOnAbility(this.clickValue);

        Timer ability1_Timer = new Timer();

        TimerTask turnOff = new TimerTask(){
            public void run() {
                GameLogic.this.setClickValue(ability2.turnOffAbility(GameLogic.this.getClickValue()));
            }
        };

        ability1_Timer.schedule(turnOff, 3000);
    }

    public void handleAbility3(final Ability ability3){
        this.clickValue = ability3.turnOnAbility(this.clickValue);

        Timer ability1_Timer = new Timer();

        TimerTask turnOff = new TimerTask(){
            public void run() {
                GameLogic.this.setClickValue(ability3.turnOffAbility(GameLogic.this.getClickValue()));
            }
        };

        ability1_Timer.schedule(turnOff, 3000);
    }

    public void handleAbility4(final Ability ability4){
        this.clickValue = ability4.turnOnAbility(this.clickValue);

        Timer ability1_Timer = new Timer();

        TimerTask turnOff = new TimerTask(){
            public void run() {
                GameLogic.this.setClickValue(ability4.turnOffAbility(GameLogic.this.getClickValue()));
            }
        };

        ability1_Timer.schedule(turnOff, 3000);
    }
}
