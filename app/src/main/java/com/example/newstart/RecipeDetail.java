package com.example.newstart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeDetail extends AppCompatActivity {
 ImageView imageView;
 TextView RecipeName,RecipeDescription,Recipe_ing;
 String RN,RD,RI;
 int IM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        imageView=(ImageView)findViewById(R.id.image_view);
        RecipeName=(TextView)findViewById(R.id.Recipe_name);
        RecipeDescription=(TextView)findViewById(R.id.Recipe_description);
        Recipe_ing=(TextView)findViewById(R.id.Recipe_ing2);

        IM=getIntent().getIntExtra("Image",0);
        RN=getIntent().getStringExtra("Recipe Name");
        RD=getIntent().getStringExtra("Recipe Description");
        RI=getIntent().getStringExtra("Recipe Ingredients");
        RecipeName.setText(RN);
        RecipeDescription.setText(RD);
        Recipe_ing.setText(RI);
    }
}
