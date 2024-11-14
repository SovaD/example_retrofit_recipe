package com.example.example_retrofit_recipe.retrofit;

public interface DataCallback <T> {
    void onSuccess(T data);
    void onFailure(Throwable throwable);
}
