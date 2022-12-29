package vendingmachine.domain.coin.repository

import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin
import vendingmachine.domain.product.model.Product

interface CoinRepository {
    fun getPossibleReturnCoins(coins : Map<Coin,Int>,
                               remainMoney: Int,
                               remainCoin : ((coin : Coin, amount : Int) -> Unit)? = null) : List<ChangeCoin>

    fun returnCoins(remainMoney : Int) : List<ChangeCoin>

    fun getReturnCoinCount(coin : Coin, coinCnt : Int, money : Int) : Int

    fun setCoins(coins : Map<Coin,Int>)

    fun getRemainCoins() : List<ChangeCoin>
}