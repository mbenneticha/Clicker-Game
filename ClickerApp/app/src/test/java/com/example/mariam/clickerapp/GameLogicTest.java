package com.example.mariam.clickerapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import android.test.suitebuilder.annotation.SmallTest;
import com.example.mariam.clickerapp.GameLogic;
import junit.framework.TestCase;
import static org.junit.Assert.*;

/**
 * Created by Mariam on 5/21/17.
 */

public class GameLogicTest extends TestCase{

    @Override
    protected void setUp() throws Exception{
        super.setUp();

    }

    //test icc() : proper update of clickCount
    //Success
    @Test
    public void testicc() throws Exception{

        GameLogic gm = new GameLogic();
        int clicks = 100;
        gm.incClicks(clicks);

        gm.icc();
        int result = gm.getClickCount();
        assertNotSame(clicks, result); //100 vs 101
        assertEquals(101, result);
    }


    //test set and get clickValue
    //Success
    @Test
    public void testGetClickValue() throws Exception {

        GameLogic gm = new GameLogic();
        double value = 1.69;
        gm.setClickValue(value);
        double result = gm.getClickValue();
        assertEquals(value, result);

        double value2 = value + 5.41;
        gm.setClickValue(value2);
        assertNotSame(value2, result);

        double result2 = gm.getClickValue();
        assertEquals(value2, result2);
        assertNotSame(value2 + value, result2);

    }

    //test incClicks and getClickCount
    //Success
    @Test
    public void testGetClickCount() throws Exception {

        GameLogic gm = new GameLogic();
        int clicks = 10;
        gm.incClicks(clicks);
        int result = gm.getClickCount();
        assertEquals(clicks, result);

        int click2 = clicks + 5;
        gm.incClicks(click2);
        assertNotSame(click2, result);

        int result2 = gm.getClickCount();
        assertNotSame(click2, result2); //-- check for failure
        assertEquals(click2 + clicks, result2);

    }

    //test icc(), getTotalCurrency(), and getCurrentCurrency()
    //icc() updates total and current currency
    //Success
    @Test
    public void testGetTotalCurrency() throws Exception {

        GameLogic gm = new GameLogic();
        double clickVal = 0.87;
        int clickCount = 100;
        gm.setClickValue(clickVal);
        gm.incClicks(clickCount);

        //use values to update gamelogic currency values (total and current)
        gm.icc();
        double total = gm.getTotalCurrency();
        double current = gm.getCurrentCurrency();

        assertEquals(clickVal, total);  //total currency is replaced...
        assertNotSame(clickVal * clickCount, total); //not incremented by icc()

        assertEquals(clickVal, current);  //current currency is replaced...
        assertNotSame(clickVal * clickCount, current); //not incremented by icc()


    }

    /*tested above with getTotalCurrency()
    @Test
    public void getCurrentCurrency() throws Exception {

    }
    */


    @Test
    public void testIncrementClickValue() throws Exception {

        GameLogic gm = new GameLogic();
        double valInc = 3.50;
        double clickVal = 11.50;
        gm.setClickValue(clickVal);
        double result = gm.getClickValue();

        assertEquals(clickVal, result); //ensure proper update of clickValue

        gm.incrementClickValue(valInc);
        double result2 = gm.getClickValue();
        assertEquals(result + valInc, result2);

    }

    @Test
    public void buyUnlockable() throws Exception {

    }

/*
    //test levelUp, unlockValue increase, level increase, set new clickValue
    //test getUnlockValue(), getLevel().
    //Success
    @Test
    public void testLevelUp() throws Exception {

        GameLogic gm = new GameLogic();
        int levelA = gm.getLevel();
        double unlockVal = 5;
        double result = gm.getUnlockValue();
        double clickVal0 = gm.getClickValue();
        assertEquals(0, levelA);
        assertEquals(unlockVal, result);
        assertEquals(gm.clickValues[0], clickVal0);

        gm.levelUp();
        int levelB = gm.getLevel();
        double result2 = gm.getUnlockValue();
        double clickVal1 = gm.getClickValue();
        assertEquals(levelA + 1, levelB);
        assertEquals(result * 3, result2);
        assertEquals(gm.clickValues[1], clickVal1);

        gm.levelUp();
        gm.levelUp();
        gm.levelUp();
        gm.levelUp();

        int level5 = gm.getLevel();
        double unlockVal5 = gm.getUnlockValue();
        double clickVal5 = gm.getClickValue();
        assertEquals(5, level5);
        assertEquals(1215.0, unlockVal5);
        assertEquals(gm.clickValues[5], clickVal5);

    }
*/


    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }

}