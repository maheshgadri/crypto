package com.crypto.cryptotask.repository

import com.crypto.cryptotask.apiservice.RetrofitInstance
import com.crypto.cryptotask.model.Coin

class CoinRepository {
    private val api = RetrofitInstance.api

    suspend fun getCoins() = api.getCoins()

    suspend fun getCoinDetails(id: String) = api.getCoinDetails(id)
}
