package vendingmachine.domain.coin.repository

import vendingmachine.domain.coin.mapper.toChangeCoinList
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin
import vendingmachine.domain.product.model.Product

class NormalCoinRepository : CoinRepository {

    private var coins : Map<Coin,Int> = mapOf()

    override fun returnCoins(remainMoney: Int): List<ChangeCoin> {
        val mutableMap = coins.toMutableMap()
        val result = getPossibleReturnCoins(coins,remainMoney) { coin, amount ->
            mutableMap[coin] = amount
        }
        coins = mutableMap
        return result
    }

    override fun getPossibleReturnCoins(
        coins : Map<Coin,Int>,
        remainMoney: Int,
        remainCoin: ((coin: Coin, amount: Int) -> Unit)?
    ) : List<ChangeCoin>  {
        val result = mutableListOf<ChangeCoin>()
        var tempRemainMoney = remainMoney
        coins.forEach { (coin, coinCnt) ->
            val returnCoinCount = getReturnCoinCount(coin, coinCnt, tempRemainMoney)
            if (returnCoinCount > 0) {
                remainCoin?.invoke(coin,coinCnt-returnCoinCount)
                tempRemainMoney-=returnCoinCount*coin.getAmount()
                val changeCoin = ChangeCoin(coin, returnCoinCount)
                result.add(changeCoin)
            }
        }
        return result
    }

    override fun getReturnCoinCount(coin: Coin, coinCnt : Int, money: Int): Int {
        var remainCoinCnt = coinCnt
        val coinAmount = coin.getAmount()
        var tempRemainMoney = money
        while (remainCoinCnt > 0 && tempRemainMoney >= coinAmount) {
            tempRemainMoney -= coinAmount
            remainCoinCnt--
        }
        return coinCnt-remainCoinCnt
    }

    override fun setCoins(coins: Map<Coin, Int>) {
        this.coins = coins
    }

    override fun getRemainCoins(): List<ChangeCoin> {
        return this.coins.toChangeCoinList()
    }
}