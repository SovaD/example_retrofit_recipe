package com.example.example_retrofit_recipe.entities;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    int id;
    String name;
    List<String> ingredients;
    List<String> instructions;
    String cuisine;
    String image;
    Float rating;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getImage() {
        return image;
    }

    public Float getRating() {
        return rating;
    }
}
