package com.crypto.cryptotask.apiservice

import com.crypto.cryptotask.utils.ApiConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CoinPaprikaApi by lazy {
        retrofit.create(CoinPaprikaApi::class.java)
    }
}

