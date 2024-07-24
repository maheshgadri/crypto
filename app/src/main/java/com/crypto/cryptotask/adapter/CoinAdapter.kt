package com.crypto.cryptotask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.crypto.cryptotask.databinding.ListItemCoinBinding
import com.crypto.cryptotask.model.Coin

class CoinAdapter(private val coins: List<Coin>, private val onItemClick: (Coin) -> Unit) :
    RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ListItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = coins[position]
        holder.bind(coin)
        holder.itemView.setOnClickListener { onItemClick(coin) }
    }

    override fun getItemCount() = coins.size

    inner class CoinViewHolder(private val binding: ListItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: Coin) {
            binding.coinName.text = coin.name
            binding.coinSymbol.text = coin.symbol
            binding.coinPrice.text = "$${coin.price_usd}"
        }
    }
}
