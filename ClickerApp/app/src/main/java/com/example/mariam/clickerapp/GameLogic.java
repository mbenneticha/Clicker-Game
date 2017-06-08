package com.example.mariam.clickerapp;


import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
    private int clickCount, level, foodLevel, waterLevel, wheelLevel, hutLevel, multiplier;
    private double clickValue, unlockValue, total_currency, current_currency;
	double[] clickValues = new double[] { 0.02, 0.03, 0.09, 0.27, 0.81, 2.43, 3.65, 10.94, 32.81, 98.42, 295.25, 442.87, 1328.60, 3985.81, 11957.43, 35872.27, 71744.54, 215233.61, 645700.82, 1937102.45, 5811307.34  };
    double[] unlockValues = new double[] { 0, 5, 15, 45, 135, 405, 1215, 3645, 10935, 32805, 98415, 295245, 885735, 2657205, 7971615, 23914845, 71744535, 215233505, 645700815, 1937102445, (1937102445*3), (1937102445*6)  };
    double[] foodCost = new double[] { 7.50, 1822.50, 442867.50, 107616802.50};
    double[] waterCost = new double[] { 22.50, 5467.50, 1328602.50, 322850407.50};
    double[] hutCost = new double[] {67.50, 16402.50, 3985807.50, 968551222.50};
    double[] wheelCost = new double[] {202.50, 49207.50, 11957422.50, 2905653667.50};
    ArrayList<Timer> timers = new ArrayList<Timer>();


    public GameLogic(){
        this.clickCount = 0;
        this.total_currency = 0.00;
        this.current_currency = 0.00;
        this.clickValue = 0.52;
        this.level = 0;
        this.unlockValue = 1;
        this.foodLevel = 0;
        this.waterLevel = 0;
        this.wheelLevel = 0;
        this.hutLevel = 0;
        this.multiplier = 1;
    }


    /* CLICK LOGIC */
    // 'icc' == 'incrementClickCount'
    // Shorted here for ease of use
    public void icc(){
        this.clickCount++;
        this.total_currency += (clickValue*multiplier);
        this.current_currency += (clickValue*multiplier);
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

    public void setClickCount(int value){
        this.clickCount = value;
    }

    public int getMultiplier() { return this.multiplier; }

    public void setMultiplier(int value) { this.multiplier = value; }


    /*CURRENCY LOGIC */
    public double getTotalCurrency(){
        DecimalFormat df = new DecimalFormat("0.00");
        Double tot_curr = new Double(df.format(this.total_currency));
        return tot_curr;
    }
    public void setTotalCurrency(double value) { this.total_currency = value; }

    public double getCurrentCurrency(){
        DecimalFormat df = new DecimalFormat("0.00");
        Double cur_curr = new Double(df.format(this.current_currency));
        return cur_curr;
    }

    public void setCurrentCurrency(double value) { this.current_currency = value; }

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

    public void setLevel(int value){
        this.level = value;
    }

    public void setUnlockValue(double value){ this.unlockValue = value; }

    public double getUnlockValue() { return this.unlockValue; }

    public void levelUp(){
        this.level++;
        this.unlockValue = unlockValues[this.level];
		setClickValue(clickValues[this.level]);

        if(this.level >= 20) {
            gameCompleteReset();
        }
    }

    public void gameCompleteReset() {
        this.clickCount = 0;
        this.total_currency = 0.00;
        this.current_currency = 0.00;
        this.clickValue = 0.02;
        this.level = 0;
        this.unlockValue = 1;
        this.foodLevel = 0;
        this.waterLevel = 0;
        this.wheelLevel = 0;
        this.hutLevel = 0;
        this.multiplier = this.multiplier*2;
        cancelAllTimers();
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

    //ABILITY LOGIC

    public void reActivateAbility(Ability ability){
        ability.setToCharged();
    }
    public void handleAbility1(final Ability ability1){
        this.clickValue = ability1.turnOnAbility(this.clickValue);
        ability1.setToRecharging();

    }

    public void handleAbility2(final Ability ability2){
        this.clickValue = ability2.turnOnAbility(this.clickValue);
        ability2.setToRecharging();
    }

    public void handleAbility3(final Ability ability3){
        this.clickValue = ability3.turnOnAbility(this.clickValue);
        ability3.setToRecharging();
    }

    public void handleAbility4(final Ability ability4){
        this.clickValue = ability4.turnOnAbility(this.clickValue);
        ability4.setToRecharging();
    }

    //UPGRADE LOGIC
    /******
     * Food Dish
     * - updates image on UI as upgrades are purchased
     * - ensures proper amount of money available for upgrade
     * - adjusts current_currency (subtracts cost of purchase)
     * - purchase can only be made once until next upgrade
     * - adds functionality of upgrade (adds $$/second in accordance to mechanics)
     * - updates upgrade_level (prep for next upgrade)
    *******/

    public int getFoodLevel(){
        return this.foodLevel;
    }

    public void setFoodLevel(int value){
        this.foodLevel = value;
    }

    public int getWaterLevel(){
        return this.waterLevel;
    }

    public void setWaterLevel(int value){
        this.waterLevel = value;
    }

    public int getHutLevel(){
        return this.hutLevel;
    }

    public void setHutLevel(int value){
        this.hutLevel = value;
    }

    public int getWheelLevel(){
        return this.wheelLevel;
    }

    public void setWheelLevel(int value){
        this.wheelLevel = value;
    }

    public double getFoodCost() { return foodCost[foodLevel]; }

    public double getWaterCost() { return waterCost[waterLevel]; }

    public double getHutCost() { return hutCost[hutLevel]; }

    public double getWheelCost() { return wheelCost[wheelLevel]; }

    public void cancelAllTimers() {
        for (int i = 0; i < timers.size(); i++) {
            timers.get(i).cancel();
        }
    }

    public void startFoodTimer(Upgrade upgrade_food) {
        switch (foodLevel){
            case 0: {
                break;
            }
            case 1: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[0];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[0];
                    }
                }, 0, 1000);

                break;
            }
            case 2: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[5];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[5];
                    }
                }, 0, 1000);
                break;
            }
            case 3: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[10];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[10];
                    }
                }, 0, 1000);
                break;
            }
            case 4: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[15];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[15];
                    }
                }, 0, 1000);
                break;
            }
            default: {
                break;
            }
        }

    }

    public void startWaterTimer(Upgrade upgrade_water) {
        switch (waterLevel){
            case 0: {
                break;
            }
            case 1: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[1];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[1];
                    }
                }, 0, 1000);

                break;
            }
            case 2: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[6];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[6];
                    }
                }, 0, 1000);
                break;
            }
            case 3: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[11];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[11];
                    }
                }, 0, 1000);
                break;
            }
            case 4: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[16];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[16];
                    }
                }, 0, 1000);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void startHutTimer(Upgrade upgrade_hut) {
        switch (hutLevel){
            case 0: {
                break;
            }
            case 1: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[2];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[2];
                    }
                }, 0, 1000);

                break;
            }
            case 2: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[7];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[7];
                    }
                }, 0, 1000);
                break;
            }
            case 3: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[12];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[12];
                    }
                }, 0, 1000);
                break;
            }
            case 4: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[17];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[17];
                    }
                }, 0, 1000);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void startWheelTimer(Upgrade upgrade_wheel) {
        switch (wheelLevel){
            case 0: {
                break;
            }
            case 1: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[3];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[3];
                    }
                }, 0, 1000);

                break;
            }
            case 2: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[8];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[8];
                    }
                }, 0, 1000);
                break;
            }
            case 3: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[13];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[13];
                    }
                }, 0, 1000);
                break;
            }
            case 4: {
                Timer timer = new Timer();
                timers.add(timer);
                timer.scheduleAtFixedRate(new TimerTask()
                {
                    public void run()
                    {
                        GameLogic.this.total_currency += GameLogic.this.clickValues[18];
                        GameLogic.this.current_currency += GameLogic.this.clickValues[18];
                    }
                }, 0, 1000);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void upgradeCheck(Upgrade upgrade_food, Upgrade upgrade_water, Upgrade upgrade_hut, Upgrade upgrade_wheel) {
        // Reset and check upgrade food
        upgrade_food.setUpgradeLevel(foodLevel);
        startFoodTimer(upgrade_food);

        // Reset and check upgrade water
        upgrade_water.setUpgradeLevel(foodLevel);
        startWaterTimer(upgrade_water);

        // Reset and check upgrade hut
        upgrade_hut.setUpgradeLevel(hutLevel);
        startHutTimer(upgrade_hut);

        // Reset and check upgrade wheel
        upgrade_wheel.setUpgradeLevel(wheelLevel);
        startWheelTimer(upgrade_wheel);

    }

    public void refreshUpgradeImages(Upgrade upgrade_food, Upgrade upgrade_water, Upgrade upgrade_hut, Upgrade upgrade_wheel) {
        upgrade_food.resetImage(foodLevel);
        upgrade_water.resetImage(waterLevel);
        upgrade_hut.resetImage(hutLevel);
        upgrade_wheel.resetImage(wheelLevel);
    }


    public void upgradeFood(Upgrade upgrade_food){
        switch (foodLevel){
            case 0: {
                if (this.current_currency >= this.foodCost[foodLevel]) {
                    this.current_currency -= this.foodCost[foodLevel];
                    upgrade_food.unlockUpgrade(1); //set to basic image
                    upgrade_food.incrementUpgradeLevel();
                    foodLevel += 1;
                    startFoodTimer(upgrade_food);
                }
                break;
            }
            case 1: {
                if (this.current_currency >= this.foodCost[foodLevel]){
                    this.current_currency -= this.foodCost[foodLevel];
                    upgrade_food.unlockUpgrade(2); //set to deluxe image -- not working
                    upgrade_food.incrementUpgradeLevel();
                    foodLevel += 1;
                    startFoodTimer(upgrade_food);
                }
                break;
            }
            case 2: {
                if (this.current_currency >= this.foodCost[foodLevel]){
                    this.current_currency -= this.foodCost[foodLevel];
                    upgrade_food.unlockUpgrade(3); //set to luxury image -- not working
                    upgrade_food.incrementUpgradeLevel();
                    foodLevel += 1;
                    startFoodTimer(upgrade_food);
                }
                break;
            }
            case 3: {
                if (this.current_currency >= this.foodCost[foodLevel]){
                    this.current_currency -= this.foodCost[foodLevel];
                    upgrade_food.unlockUpgrade(4); //set to gold image -- not working
                    upgrade_food.incrementUpgradeLevel();
                    foodLevel += 1;
                    startFoodTimer(upgrade_food);
                }
                break;
            }
            default:
             //   upgrade_food.unlockUpgrade(0, upgrade_food); //set to locked image -- not working
                break;
        }
    }

    //Water Bottle
    public void upgradeWater(Upgrade upgrade_water){
        switch (waterLevel) {
            case 0: {
                if (this.current_currency >= this.waterCost[waterLevel]) {
                        this.current_currency -= this.waterCost[waterLevel];
                        upgrade_water.unlockUpgrade(1); //set to basic image
                        upgrade_water.incrementUpgradeLevel();
                        waterLevel += 1;
                        startWaterTimer(upgrade_water);
                }
                break;
            }
            case 1: {
                if (this.current_currency >= this.waterCost[waterLevel]){
                    this.current_currency -= this.waterCost[waterLevel];
                    upgrade_water.unlockUpgrade(2); //set to deluxe image -- not working
                    upgrade_water.incrementUpgradeLevel();
                    waterLevel += 1;
                    startWaterTimer(upgrade_water);
                }
                break;
            }
            case 2: {
                if (this.current_currency >= this.waterCost[waterLevel]){
                    this.current_currency -= this.waterCost[waterLevel];
                    upgrade_water.unlockUpgrade(3); //set to luxury image -- not working
                    upgrade_water.incrementUpgradeLevel();
                    waterLevel += 1;
                    startWaterTimer(upgrade_water);
                }
                break;
            }
            case 3: {
                if (this.current_currency >= this.waterCost[waterLevel]){
                    this.current_currency -= this.waterCost[waterLevel];
                    upgrade_water.unlockUpgrade(4); //set to gold image -- not working
                    upgrade_water.incrementUpgradeLevel();
                    waterLevel += 1;
                    startWaterTimer(upgrade_water);
                }
                break;
            }
            default:
            //    upgrade_water.unlockUpgrade(0, upgrade_water); //set to locked image -- not working
                break;
        }
    }

    //Hamster Hut
    public void upgradeHut(Upgrade upgrade_hut){
        switch (hutLevel){
            case 0: {
                if (this.current_currency >= this.hutCost[hutLevel]){
                    this.current_currency -= this.hutCost[hutLevel];
                    upgrade_hut.unlockUpgrade(1); //set to basic image
                    upgrade_hut.incrementUpgradeLevel();
                    hutLevel += 1;
                    startHutTimer(upgrade_hut);
                }
                break;
            }
            case 1: {
                if (this.current_currency >= this.hutCost[hutLevel]){
                    this.current_currency -= this.hutCost[hutLevel];
                    upgrade_hut.unlockUpgrade(2); //set to deluxe image -- not working
                    upgrade_hut.incrementUpgradeLevel();
                    hutLevel += 1;
                    startHutTimer(upgrade_hut);
                }
                break;
            }
            case 2: {
                if (this.current_currency >= this.hutCost[hutLevel]){
                    this.current_currency -= this.hutCost[hutLevel];
                    upgrade_hut.unlockUpgrade(3); //set to luxury image -- not working
                    upgrade_hut.incrementUpgradeLevel();
                    hutLevel += 1;
                    startHutTimer(upgrade_hut);
                }
                break;
            }
            case 3: {
                if (this.current_currency >= this.hutCost[hutLevel]){
                    this.current_currency -= this.hutCost[hutLevel];
                    upgrade_hut.unlockUpgrade(4); //set to gold image -- not working
                    upgrade_hut.incrementUpgradeLevel();
                    hutLevel += 1;
                    startHutTimer(upgrade_hut);
                }
                break;
            }
            default: {
                upgrade_hut.unlockUpgrade(0); //set to locked image -- not working
                break;
            }
        }
    }

    //Hamster Wheel
    public void upgradeWheel(Upgrade upgrade_wheel){
        switch (wheelLevel){
            case 0: {
                if (this.current_currency >= this.wheelCost[wheelLevel]){
                    this.current_currency -= this.wheelCost[wheelLevel];
                    upgrade_wheel.unlockUpgrade(1); //set to basic image
                    upgrade_wheel.incrementUpgradeLevel();
                    wheelLevel += 1;
                    startWheelTimer(upgrade_wheel);
                }
                break;
            }
            case 1: {
                if (this.current_currency >= this.wheelCost[wheelLevel]){
                    this.current_currency -= this.wheelCost[wheelLevel];
                    upgrade_wheel.unlockUpgrade(2); //set to deluxe image -- not working
                    upgrade_wheel.incrementUpgradeLevel();
                    wheelLevel += 1;
                    startWheelTimer(upgrade_wheel);
                }
                break;
            }
            case 2: {
                if (this.current_currency >= this.wheelCost[wheelLevel]){
                    this.current_currency -= this.wheelCost[wheelLevel];
                    upgrade_wheel.unlockUpgrade(3); //set to luxury image -- not working
                    upgrade_wheel.incrementUpgradeLevel();
                    wheelLevel += 1;
                    startWheelTimer(upgrade_wheel);
                }
                break;
            }
            case 3: {
                if (this.current_currency >= this.wheelCost[wheelLevel]){
                    this.current_currency -= this.wheelCost[wheelLevel];
                    upgrade_wheel.unlockUpgrade(4); //set to gold image -- not working
                    upgrade_wheel.incrementUpgradeLevel();
                    wheelLevel += 1;
                    startWheelTimer(upgrade_wheel);
                }
                break;
            }
            default: {
                upgrade_wheel.unlockUpgrade(0); //set to locked image -- not working
                break;
            }
        }
    }

}
