package com.crypto.cryptotask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.crypto.cryptotask.adapter.CoinAdapter
import com.crypto.cryptotask.databinding.ActivityMainBinding
import com.crypto.cryptotask.model.Coin
import com.crypto.cryptotask.repository.CoinRepository
import com.crypto.cryptotask.ui.CoinDetailActivity
import com.crypto.cryptotask.viewmodel.CoinViewModel
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CoinViewModel
    private lateinit var coinAdapter: CoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)

        coinAdapter = CoinAdapter(emptyList()) { coin ->
            val intent = Intent(this, CoinDetailActivity::class.java)
            intent.putExtra("coin_id", coin.id)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = coinAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchCoins()
        }

        binding.searchEditText.addTextChangedListener {
            viewModel.searchCoins(it.toString())
        }

        viewModel.coins.observe(this, {
            coinAdapter = CoinAdapter(it) { coin ->
                val intent = Intent(this, CoinDetailActivity::class.java)
                intent.putExtra("coin_id", coin.id)
                startActivity(intent)
            }
            binding.recyclerView.adapter = coinAdapter
        })

        viewModel.loading.observe(this, {
            binding.swipeRefreshLayout.isRefreshing = it
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.fetchCoins()
    }
}

