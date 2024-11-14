package com.example.example_retrofit_recipe.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeList {
    @SerializedName("recipes")
    private List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
