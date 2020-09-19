package com.android.goodslistgambit.database;

import android.content.SharedPreferences;

import com.android.goodslistgambit.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App {
    private SharedPreferences mSharedPreferences;


    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.gambit-app.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static ApiService apiService = retrofit.create(ApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }
}
