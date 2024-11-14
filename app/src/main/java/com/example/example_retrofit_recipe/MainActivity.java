package com.example.example_retrofit_recipe;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_retrofit_recipe.activities.ContentActivity;
import com.example.example_retrofit_recipe.adapters.CategoryAdapter;
import com.example.example_retrofit_recipe.adapters.ItemAdapter;
import com.example.example_retrofit_recipe.entities.Recipe;
import com.example.example_retrofit_recipe.entities.RecipeList;
import com.example.example_retrofit_recipe.retrofit.ApiService;
import com.example.example_retrofit_recipe.retrofit.DataCallback;
import com.example.example_retrofit_recipe.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "taggggg";

    RecyclerView categoriesView, recipesView;
    EditText textToSearch;

    List<Recipe> allRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recipesView = findViewById(R.id.itemsList);
        categoriesView = findViewById(R.id.categoryList);
        textToSearch = findViewById(R.id.tToSearch);

        fetchData(new DataCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> data) {
                // Здесь доступ к данным во внешней переменной
                allRecipes = data;

                fillRecipeView(allRecipes,"all");
                fillCategories(allRecipes, getCategoriesList(allRecipes));
                Log.i(TAG, allRecipes.size() + "");

                textToSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        filterRecipe();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Обработка ошибки
                Log.e("API_ERROR", "Ошибка при загрузке данных", throwable);
            }
        });
    }

    private void filterRecipe() {
        String text = textToSearch.getText().toString().toLowerCase();
        List<Recipe> newList = allRecipes.stream()
                .filter(recipe -> recipe.getCuisine().toLowerCase().contains(text)
                        || recipe.getName().toLowerCase().contains(text)
                        || recipe.getIngredients().contains(equals(text)))
                .collect(Collectors.toList());
        fillRecipeView(newList,"all");
    }

    void fetchData(DataCallback<List<Recipe>> callback) {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        try {

            // Выполняем запрос
            Call<RecipeList> call = apiService.getRecipes();
            call.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        RecipeList recipeList = response.body();
                        List<Recipe> recipes = recipeList.getRecipes();
                        callback.onSuccess(recipes);
                    } else {
                        Log.e(TAG, "Response error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable t) {
                    Log.e(TAG, "Network error: ", t);

                }
            });
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());

        }

    }


    void fillRecipeView(List<Recipe> list, String category) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recipesView.setLayoutManager(linearLayoutManager);

        if (category.toLowerCase().equals("all")) {
            ItemAdapter adapter = new ItemAdapter(MainActivity.this, list);
            adapter.setOnClickListener(new ItemAdapter.OnClickListener() {
                @Override
                public void onClick(int position, Recipe recipe) {
                    Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                    intent.putExtra("recipe",recipe);
                    startActivity(intent);
                    Log.i(TAG,"clicked");
                }
            });
            recipesView.setAdapter(adapter);
            return;
        }
        List<Recipe> newList = list.stream()
                .filter(recipe -> recipe.getCuisine().equals(category))
                .collect(Collectors.toList());
        ItemAdapter adapter = new ItemAdapter(MainActivity.this, newList);
        adapter.setOnClickListener(new ItemAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Recipe recipe) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("recipe",recipe);
                startActivity(intent);
                Log.i(TAG,"clicked");
            }
        });
        recipesView.setAdapter(adapter);
    }

    void fillCategories(List<Recipe> recipes, List<String> categoryList) {
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoriesView.setLayoutManager(linearLayoutManager);

        CategoryAdapter adapter = new CategoryAdapter(MainActivity.this, categoryList);

        adapter.setOnClickListener(new CategoryAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String category) {
                adapter.notifyItemChanged(adapter.selectedPosition);
                adapter.selectedPosition = position;
                adapter.notifyItemChanged(adapter.selectedPosition);
                fillRecipeView(recipes, category);

            }
        });
        categoriesView.setAdapter(adapter);
    }

    List<String> getCategoriesList(List<Recipe> recipes) {
        List<String> categoryList = recipes.stream()
                .map(Recipe::getCuisine)
                .distinct()
                .collect(Collectors.toList());
        categoryList.add(0, "All");
        Collections.sort(categoryList);
        return categoryList;
    }
}