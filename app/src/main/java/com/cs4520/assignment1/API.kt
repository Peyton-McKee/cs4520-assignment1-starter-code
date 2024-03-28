package com.cs4520.assignment1

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("prod")
    fun listProducts(@Query("page") page: Int?): Call<List<ProductItemAPIResponse>>

    @GET("prod/random")
    fun randomProduct(): Call<List<ProductItemAPIResponse>>
}

object API {
    private const val baseUrl = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
