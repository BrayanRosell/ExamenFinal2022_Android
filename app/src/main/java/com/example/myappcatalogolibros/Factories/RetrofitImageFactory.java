package com.example.myappcatalogolibros.Factories;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitImageFactory {

    public static Retrofit build(Context context) {


        return new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    private static OkHttpClient getClient() {
        OkHttpClient httpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Authorization", "Client-ID 8bcc638875f89d9");
//                requestBuilder.header("X-Amz-Content-Sha256", "");
                return chain.proceed(requestBuilder.build());
            }
        }).build();


        return httpClient;
    }
}
