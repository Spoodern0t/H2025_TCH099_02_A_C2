package com.example.multuscalendrius.modeles.entitees;
interface ApiCallback<T> {
    void onSuccess(T result);
    void onFailure(String errorMessage);
}