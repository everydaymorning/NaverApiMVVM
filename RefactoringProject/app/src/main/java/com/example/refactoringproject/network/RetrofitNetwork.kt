package com.example.refactoringproject.network

import com.example.refactoringproject.R
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
        private const val BASE_URL_API = "https://openapi.naver.com/"
        private const val CLIENT_ID = "L0tYinrnwRaZ6DzIACHl"
        private const val CLIENT_SECRET = "JCMvS1s13s"

        fun create(): RetrofitNetwork{
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor{
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitNetwork::class.java)
        }
    }
}