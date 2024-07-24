package com.crypto.cryptotask.apiservice

import com.crypto.cryptotask.model.Coin
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {
    @GET("v1/coins")
    suspend fun getCoins(): List<Coin>

    @GET("v1/coins/{id}")
    suspend fun getCoinDetails(@Path("id") id: String): Coin
}
