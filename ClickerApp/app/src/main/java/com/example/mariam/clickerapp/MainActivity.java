package com.example.mariam.clickerapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int clickedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.clickedCount = 0;
        this.updateUI();
    }

    public void primaryClick(View view){
        this.clickedCount++;

        this.updateUI();
    }

    public void abilityClick(View view){
        Context context = getApplicationContext();
        String text = "Ability Clicked!";
        Toast abilityToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        abilityToast.show();
    }

    public void updateUI(){
        TextView clickCountText = (TextView) findViewById(R.id.click_number_output);
        String count = Integer.toString(clickedCount);
        clickCountText.setText(count);
    }

}
