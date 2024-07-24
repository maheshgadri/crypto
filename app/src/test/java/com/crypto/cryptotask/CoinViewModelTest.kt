package com.crypto.cryptotask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crypto.cryptotask.model.Coin
import com.crypto.cryptotask.repository.CoinRepository
import com.crypto.cryptotask.viewmodel.CoinViewModel
import getOrAwaitValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class CoinViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CoinViewModel
    private val repository = mock(CoinRepository::class.java)

    @Before
    fun setUp() {
        viewModel = CoinViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun fetchCoins_success() = runBlocking {
        val coins = listOf(
            Coin("1", "Bitcoin", "BTC", 1, 50000.0, 1.0, 5000000.0, 1000000000.0),
            Coin("2", "Ethereum", "ETH", 2, 4000.0, 0.08, 4000000.0, 400000000.0)
        )
        `when`(repository.getCoins()).thenReturn(coins)

        viewModel.fetchCoins()

        assertEquals(coins, viewModel.coins.getOrAwaitValue())
    }

    @Test
    fun fetchCoinDetails_success() = runBlocking {
        val coin = Coin("1", "Bitcoin", "BTC", 1, 50000.0, 1.0, 5000000.0, 1000000000.0)
        `when`(repository.getCoinDetails("1")).thenReturn(coin)

        viewModel.fetchCoinDetails("1")

        assertEquals(coin, viewModel.coinDetails.getOrAwaitValue())
    }

    @Test
    fun searchCoins_success() {
        val coins = listOf(
            Coin("1", "Bitcoin", "BTC", 1, 50000.0, 1.0, 5000000.0, 1000000000.0),
            Coin("2", "Ethereum", "ETH", 2, 4000.0, 0.08, 4000000.0, 400000000.0)
        )
        viewModel._coins.value = coins

        viewModel.searchCoins("Bitcoin")

        val result = viewModel.coins.getOrAwaitValue()
        assertEquals(1, result.size)
        assertEquals("Bitcoin", result[0].name)
    }
}
