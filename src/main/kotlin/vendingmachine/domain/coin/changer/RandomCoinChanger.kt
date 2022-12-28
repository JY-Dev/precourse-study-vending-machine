package vendingmachine.domain.coin.changer

import camp.nextstep.edu.missionutils.Randoms
import vendingmachine.domain.coin.model.Coin

class RandomCoinChanger : CoinChanger{
    override fun changeCoins(money: Int): Map<Coin, Int> {
        val coinsMap = getCoinsMap()
        var tempMoney = money
        while (tempMoney > 0){
            val coin = getCoin(money)
            tempMoney-=coin.getAmount()
            saveCoin(coinsMap, coin)
        }
        return coinsMap
    }

    private fun saveCoin(
        coinsMap: LinkedHashMap<Coin, Int>,
        coin: Coin
    ) {
        coinsMap[coin] = coinsMap[coin]?.plus(1) ?: 1
    }

    private fun getCoin(money: Int) : Coin {
        val amount = Randoms.pickNumberInList(Coin.getCoinsAmount {
            it <= money
        })
        return Coin.amountToCoin(amount)
    }

    private fun getCoinsMap(): LinkedHashMap<Coin, Int> {
        val coinsMap = LinkedHashMap<Coin, Int>().apply {
            Coin.values().forEach {
                put(it, 0)
            }
        }
        return coinsMap
    }

}