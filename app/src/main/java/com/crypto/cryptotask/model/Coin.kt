package com.crypto.cryptotask.model

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val price_usd: Double,
    val price_btc: Double,
    val volume_24h_usd: Double,
    val market_cap_usd: Double
)

