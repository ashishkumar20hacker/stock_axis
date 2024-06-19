package com.example.stockaxistask.DataSource

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("index.aspx")
    fun getProductData(
        @Query("action") action: String,
        @Query("activity") activity: String,
        @Query("CID") CID: String,
        @Query("PKGName") PKGName: String
    ): Call<ApiModel>

}