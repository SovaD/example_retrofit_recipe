package com.example.example_retrofit_recipe.retrofit;

import com.example.example_retrofit_recipe.entities.RecipeList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("recipes")
    Call<RecipeList> getRecipes();
}
