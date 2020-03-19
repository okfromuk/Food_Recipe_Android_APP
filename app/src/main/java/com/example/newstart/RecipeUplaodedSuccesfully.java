package com.example.newstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecipeUplaodedSuccesfully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_uplaoded_succesfully);
    }

    public void back(View view) {
        Intent i = new Intent(RecipeUplaodedSuccesfully.this, StartPage.class);
        startActivity(i);
        finish();
    }
}
