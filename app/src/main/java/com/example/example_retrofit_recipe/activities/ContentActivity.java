package com.example.example_retrofit_recipe.activities;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.example_retrofit_recipe.R;
import com.example.example_retrofit_recipe.entities.Recipe;

import java.util.Arrays;

public class ContentActivity extends AppCompatActivity {

    TextView tName,tInstructions, tCuisine;
    ListView lvIngredients;
    ImageView imageView;
    RatingBar ratingBar;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recipe=(Recipe) getIntent().getSerializableExtra("recipe");

        tName=findViewById(R.id.tName);
        tCuisine=findViewById(R.id.cuisine);
        tInstructions=findViewById(R.id.instructions);
        lvIngredients=findViewById(R.id.ingredients);
        imageView=findViewById(R.id.image);
        ratingBar=findViewById(R.id.rating);

        tName.setText(recipe.getName());
        tCuisine.setText(recipe.getCuisine());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recipe.getIngredients());
        lvIngredients.setAdapter(adapter);
        String desc = String.join(" ",recipe.getInstructions());
        tInstructions.setText(desc);
        Glide.with(getApplicationContext()).load(recipe.getImage()).into(imageView);
        ratingBar.setRating(recipe.getRating());

    }
}