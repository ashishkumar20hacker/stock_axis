package com.example.stockaxistask.DataSource;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHandler {

    private static final String TAG = "ApiHandler";

    public static void getOrders(String PKGName, ApiResponseInterface apiResponseInterface) {
        ApiService apiService = RetrofitClient.getInstance().getClient().create(ApiService.class);
        Call<ApiModel> call = apiService.getProductData("search", "PricingV2", "984493", PKGName);
        call.enqueue(new Callback<ApiModel>() {
            @Override
            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {

                if (response.isSuccessful() && response.body() != null) {

                    ApiModel root = response.body();
                    apiResponseInterface.onSuccess(root.getData());

                } else {
                    Log.e(TAG, "Response Code >> " + response.code());
                    // Handle unsuccessful response
                    apiResponseInterface.onError();
                }

            }

            @Override
            public void onFailure(Call<ApiModel> call, Throwable t) {
                apiResponseInterface.onError();
            }
        });
    }

    public interface ApiResponseInterface {

        void onSuccess(List<Data> orderlist);

        void onError();

    }
}