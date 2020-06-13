package com.example.refactoringproject.network

import com.example.refactoringproject.R
import com.example.refactoringproject.data.Shopping
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitNetwork {

    @GET("v1/search/shop.json")
    fun getShoppingItem(
        @Query("query") query: String,
        @Query("display") display: Int = 20,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "sim"
    ): Call<Shopping>


    companion object{
        private const val BASE_URL_API = "https://openapi.naver.com/"
        private const val CLIENT_ID = "L0tYinrnwRaZ6DzIACHl"
        private const val CLIENT_SECRET = "JCMvS1s13s"

        fun create(): RetrofitNetwork{

            return Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitNetwork::class.java)
        }
    }
}