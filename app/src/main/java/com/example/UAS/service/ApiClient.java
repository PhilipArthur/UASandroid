package com.example.UAS.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getBookService(){
        return getRetrofit().create(ApiService.class);
    }
}
