package com.example.multuscalendrius.modeles.entitees;

public interface ApiCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}
