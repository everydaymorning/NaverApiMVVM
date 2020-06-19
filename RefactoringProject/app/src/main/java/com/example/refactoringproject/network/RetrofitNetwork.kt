package com.example.refactoringproject.network

import com.example.refactoringproject.R
import com.example.refactoringproject.constant.ApiConstant
import com.example.refactoringproject.data.Shopping
import com.example.refactoringproject.data.ShoppingItem
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitNetwork {

    @GET("v1/search/shop.json")
    fun getShoppingItem(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String
    ): Call<ShoppingItem>


    companion object{
        fun create(): RetrofitNetwork{

            return Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitNetwork::class.java)
        }
    }
}