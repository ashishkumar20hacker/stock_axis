package com.example.stockaxistask.DataSource;

import com.example.stockaxistask.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static RetrofitClient apiClient;
    private static final String BASE_URL = "https://www.stockaxis.com/webservices/android/";

    private Retrofit retrofit  = null;

    public static RetrofitClient getInstance(){
        if (apiClient == null){
            apiClient = new RetrofitClient();
        }
        return apiClient;
    }

    public Retrofit getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);   // development build
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);    // production build
        }
        client.addInterceptor(interceptor);
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client.build())
                .addConverterFactory(new CustomResponseConverterFactory(gson))
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit;
    }

}