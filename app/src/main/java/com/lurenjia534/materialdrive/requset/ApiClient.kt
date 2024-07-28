package com.lurenjia534.materialdrive.requset

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ApiClient.kt
object ApiClient {
    private const val BASE_URL = "https://login.microsoftonline.com/"

    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
