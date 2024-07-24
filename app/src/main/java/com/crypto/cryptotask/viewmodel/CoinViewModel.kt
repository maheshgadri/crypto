package com.crypto.cryptotask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.cryptotask.model.Coin
import com.crypto.cryptotask.repository.CoinRepository
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoinRepository()
    val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> get() = _coins

    private val _coinDetails = MutableLiveData<Coin>()
    val coinDetails: LiveData<Coin> get() = _coinDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCoins() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _coins.value = repository.getCoins()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchCoinDetails(id: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _coinDetails.value = repository.getCoinDetails(id)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun searchCoins(query: String) {
        _coins.value = _coins.value?.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.symbol.contains(query, ignoreCase = true)
        }
    }
}

