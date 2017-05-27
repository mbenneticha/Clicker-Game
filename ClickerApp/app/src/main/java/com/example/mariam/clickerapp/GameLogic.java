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
	double[] clickValues = new double[] { 0.01, 0.03, 0.09, 0.27, 0.81, 2.43, 3.65, 10.94, 32.81, 98.42, 295.25, 442.87, 1328.60, 3985.81, 11957.43, 35872.27, 71744.54, 215233.61, 645700.82, 1937102.45, 5811307.34  };
    double[] foodCost = new double[] { 7.50, 1822.50, 442867.50, 107616802.50};
    double[] waterCost = new double[] { 22.50, 5467.50, 1328602.50, 322850407.50};
    double[] hutCost = new double[] {67.50, 16402.50, 3985807.50, 968551222.50};
    double[] wheelCost = new double[] {202.50, 49207.50, 11957422.50, 2905653667.50};


    public GameLogic(){
        this.clickCount = 0;
        this.total_currency = 0.00;
        this.current_currency = 0.00;
        this.clickValue = 0.01;
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
		setClickValue(clickValues[this.level]);
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
    public void upgradeFood(Upgrade upgrade_food){
        int upgrade_level = upgrade_food.getUpgradeLevel();
        switch (upgrade_level){
            case 0:
                if (this.current_currency >= foodCost[upgrade_level]){
                    this.current_currency -= foodCost[upgrade_level];
                    upgrade_food.unlockUpgrade(1, upgrade_food); //set to basic image
                    upgrade_food.incrementUpgradeLevel();
                    //upgrade_food.setImageBasic(); --try this...not working
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[0];
                            GameLogic.this.current_currency += clickValues[0];
                        }
                    }, 0, 1000);
                }
            case 1:
                if (this.current_currency >= foodCost[upgrade_level]){
                    this.current_currency -= foodCost[upgrade_level];
                    upgrade_food.unlockUpgrade(2, upgrade_food); //set to deluxe image -- not working
                    upgrade_food.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[5];
                            GameLogic.this.current_currency += clickValues[5];
                        }
                    }, 0, 1000);
                }
            case 2:
                if (this.current_currency >= foodCost[upgrade_level]){
                    this.current_currency -= foodCost[upgrade_level];
                    upgrade_food.unlockUpgrade(3, upgrade_food); //set to luxury image -- not working
                    upgrade_food.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[10];
                            GameLogic.this.current_currency += clickValues[10];
                        }
                    }, 0, 1000);
                }
            case 3:
                if (this.current_currency >= foodCost[upgrade_level]){
                    this.current_currency -= foodCost[upgrade_level];
                    upgrade_food.unlockUpgrade(4, upgrade_food); //set to gold image -- not working
                    upgrade_food.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[15];
                            GameLogic.this.current_currency += clickValues[15];
                        }
                    }, 0, 1000);
                }
            default:
                upgrade_food.unlockUpgrade(0, upgrade_food); //set to locked image -- not working
                break;
        }
    }

    //Water Bottle
    public void upgradeWater(Upgrade upgrade_water){
        int upgrade_level = upgrade_water.getUpgradeLevel();
        switch (upgrade_level){
            case 0:
                if (this.current_currency >= waterCost[upgrade_level]){
                    this.current_currency -= waterCost[upgrade_level];
                    upgrade_water.unlockUpgrade(1, upgrade_water); //set to basic image
                    upgrade_water.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[1];
                            GameLogic.this.current_currency += clickValues[1];
                        }
                    }, 0, 1000);
                }
            case 1:
                if (this.current_currency >= waterCost[upgrade_level]){
                    this.current_currency -= waterCost[upgrade_level];
                    upgrade_water.unlockUpgrade(2, upgrade_water); //set to deluxe image -- not working
                    upgrade_water.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[6];
                            GameLogic.this.current_currency += clickValues[6];
                        }
                    }, 0, 1000);
                }
            case 2:
                if (this.current_currency >= waterCost[upgrade_level]){
                    this.current_currency -= waterCost[upgrade_level];
                    upgrade_water.unlockUpgrade(3, upgrade_water); //set to luxury image -- not working
                    upgrade_water.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[11];
                            GameLogic.this.current_currency += clickValues[11];
                        }
                    }, 0, 1000);
                }
            case 3:
                if (this.current_currency >= waterCost[upgrade_level]){
                    this.current_currency -= waterCost[upgrade_level];
                    upgrade_water.unlockUpgrade(4, upgrade_water); //set to gold image -- not working
                    upgrade_water.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[16];
                            GameLogic.this.current_currency += clickValues[16];
                        }
                    }, 0, 1000);
                }
            default:
                upgrade_water.unlockUpgrade(0, upgrade_water); //set to locked image -- not working
                break;
        }
    }

    //Hamster Hut
    public void upgradeHut(Upgrade upgrade_hut){
        int upgrade_level = upgrade_hut.getUpgradeLevel();
        switch (upgrade_level){
            case 0:
                if (this.current_currency >= hutCost[upgrade_level]){
                    this.current_currency -= hutCost[upgrade_level];
                    upgrade_hut.unlockUpgrade(1, upgrade_hut); //set to basic image
                    upgrade_hut.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[2];
                            GameLogic.this.current_currency += clickValues[2];
                        }
                    }, 0, 1000);
                }
            case 1:
                if (this.current_currency >= hutCost[upgrade_level]){
                    this.current_currency -= hutCost[upgrade_level];
                    upgrade_hut.unlockUpgrade(2, upgrade_hut); //set to deluxe image -- not working
                    upgrade_hut.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[7];
                            GameLogic.this.current_currency += clickValues[7];
                        }
                    }, 0, 1000);
                }
            case 2:
                if (this.current_currency >= hutCost[upgrade_level]){
                    this.current_currency -= hutCost[upgrade_level];
                    upgrade_hut.unlockUpgrade(3, upgrade_hut); //set to luxury image -- not working
                    upgrade_hut.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[12];
                            GameLogic.this.current_currency += clickValues[12];
                        }
                    }, 0, 1000);
                }
            case 3:
                if (this.current_currency >= hutCost[upgrade_level]){
                    this.current_currency -= hutCost[upgrade_level];
                    upgrade_hut.unlockUpgrade(4, upgrade_hut); //set to gold image -- not working
                    upgrade_hut.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[17];
                            GameLogic.this.current_currency += clickValues[17];
                        }
                    }, 0, 1000);
                }
            default:
                upgrade_hut.unlockUpgrade(0, upgrade_hut); //set to locked image -- not working
                break;
        }
    }

    //Hamster Wheel
    public void upgradeWheel(Upgrade upgrade_wheel){
        int upgrade_level = upgrade_wheel.getUpgradeLevel();
        switch (upgrade_level){
            case 0:
                if (this.current_currency >= wheelCost[upgrade_level]){
                    this.current_currency -= wheelCost[upgrade_level];
                    upgrade_wheel.unlockUpgrade(1, upgrade_wheel); //set to basic image
                    upgrade_wheel.incrementUpgradeLevel();
                    //upgrade_food.setImageBasic(); --try this...not working
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[3];
                            GameLogic.this.current_currency += clickValues[3];
                        }
                    }, 0, 1000);
                }
            case 1:
                if (this.current_currency >= wheelCost[upgrade_level]){
                    this.current_currency -= wheelCost[upgrade_level];
                    upgrade_wheel.unlockUpgrade(2, upgrade_wheel); //set to deluxe image -- not working
                    upgrade_wheel.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[8];
                            GameLogic.this.current_currency += clickValues[8];
                        }
                    }, 0, 1000);
                }
            case 2:
                if (this.current_currency >= wheelCost[upgrade_level]){
                    this.current_currency -= wheelCost[upgrade_level];
                    upgrade_wheel.unlockUpgrade(3, upgrade_wheel); //set to luxury image -- not working
                    upgrade_wheel.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[13];
                            GameLogic.this.current_currency += clickValues[13];
                        }
                    }, 0, 1000);
                }
            case 3:
                if (this.current_currency >= wheelCost[upgrade_level]){
                    this.current_currency -= wheelCost[upgrade_level];
                    upgrade_wheel.unlockUpgrade(4, upgrade_wheel); //set to gold image -- not working
                    upgrade_wheel.incrementUpgradeLevel();
                    Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask()
                    {
                        public void run()
                        {
                            GameLogic.this.total_currency += clickValues[18];
                            GameLogic.this.current_currency += clickValues[18];
                        }
                    }, 0, 1000);
                }
            default:
                upgrade_wheel.unlockUpgrade(0, upgrade_wheel); //set to locked image -- not working
                break;
        }
    }

}
