package com.example.multuscalendrius.vuemodele;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
