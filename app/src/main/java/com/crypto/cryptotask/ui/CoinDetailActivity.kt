package com.crypto.cryptotask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.crypto.cryptotask.R
import com.crypto.cryptotask.databinding.ActivityCoinDetailBinding
import com.crypto.cryptotask.model.Coin
import com.crypto.cryptotask.viewmodel.CoinViewModel

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)

        val coinId = intent.getStringExtra("coin_id")
        coinId?.let {
            viewModel.fetchCoinDetails(it)
        }

        viewModel.coinDetails.observe(this, {
            binding.coinName.text = it.name
            binding.coinSymbol.text = it.symbol
            binding.coinPrice.text = "$${it.price_usd}"
            binding.coinMarketCap.text = "Market Cap: $${it.market_cap_usd}"
            binding.coinVolume.text = "24h Volume: $${it.volume_24h_usd}"
        })

        viewModel.loading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}
