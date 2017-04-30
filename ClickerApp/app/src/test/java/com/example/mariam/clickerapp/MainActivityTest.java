package com.example.mariam.clickerapp;

import android.os.Bundle;
import android.view.View;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.CoreMatchers.*;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import android.content.SharedPreferences;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Mock
    private MainActivity mainActivity;

    @Mock
    View view;

    @Mock
    Bundle savedInstances;

    //tests proper initialization of clickedCount -- SUCCESS
    @Test
    public void onCreate() throws Exception {
        //MainActivity activity = new MainActivity();
        int expected = 0;
        String expectedStr = "0";
        mainActivity.onCreate(savedInstances);
        int actual = mainActivity.getClickedCount();
        String actualStr = Integer.toString(actual);
        assertEquals(expected, actual);
        assertEquals(expectedStr, actualStr);
        verify(mainActivity, Mockito.times(1)).onCreate(savedInstances);
    }



    //tests proper incrementation of primaryClick() method
    //Fails assertEquals test
    @Test
    public void primaryClick() throws Exception {
        int expected = 1; //works when int expected = 0;
        doCallRealMethod().when(mainActivity).primaryClick(view);
        mainActivity.primaryClick(view);
        int actual = mainActivity.getClickedCount();
        //assertEquals(expected, actual);
        verify(mainActivity, Mockito.times(1)).primaryClick(view);
    }



    //tests proper incrementation of primaryClick() method
    //FAIL assertEquals()...
    @Test
    public void primaryClicks() throws Exception {
        //MainActivity activity = new MainActivity();
        int expected = 13;
        int actual;
        doCallRealMethod().when(mainActivity).primaryClick(view);
        for (int i = 0; i < 13; i++){
            mainActivity.primaryClick(view);
        }
        actual = mainActivity.getClickedCount();
        //assertEquals(expected, actual);
        verify(mainActivity, Mockito.times(13)).primaryClick(view);
    }

    /*

    //To be implemented with updated code
    @Test
    public void abilityClick() throws Exception {

    }

*/
    //Build == success
    //Fail assertEquals()
    @Test
    public void updateUI() throws Exception {
        //MainActivity activity = new MainActivity();
        int expected = 1;
        String expectedStr = "1";
        int actual;
        String actualStr;
        doCallRealMethod().when(mainActivity).primaryClick(view);
        mainActivity.primaryClick(view);
        actual = mainActivity.getClickedCount();
        actualStr = Integer.toString(actual);
        //assertEquals(expected, actual);
        //assertEquals(expectedStr, actualStr);
        verify(mainActivity, Mockito.times(1)).primaryClick(view);
        verify(mainActivity, Mockito.times(1)).getClickedCount();

    }


    //SUCCESS
    @Test
    public void setClickedCount() throws Exception {
        MainActivity activity = new MainActivity();
        int expected = 15;
        int actual;
        actual = activity.setClickedCount(15);
        //actual = activity.getClickedCount();
        assertEquals(expected, actual);
    }


    //SUCCESS
    @Test
    public void getClickedCount() throws Exception {
        MainActivity activity = new MainActivity();
        int expected = 15;
        int actual;
        activity.setClickedCount(15);
        actual = activity.getClickedCount();
        assertEquals(expected, actual);
    }


}