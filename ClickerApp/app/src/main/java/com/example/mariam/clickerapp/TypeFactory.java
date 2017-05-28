package com.example.mariam.clickerapp;


import android.content.Context;
import android.graphics.Typeface;

public class TypeFactory {

    private String SE_REGULAR = "SueEllenFrancisco.ttf";
    Typeface sueEllenRegular;

    public TypeFactory(Context context){
        sueEllenRegular = Typeface.createFromAsset(context.getAssets(), "fonts/SueEllenFrancisco.ttf");
    }

}
