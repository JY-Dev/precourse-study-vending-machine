package vendingmachine.domain.coin.changer

import camp.nextstep.edu.missionutils.Randoms
import vendingmachine.domain.coin.model.Coin
import java.lang.IllegalArgumentException

class RandomCoinChanger : CoinChanger{
    override fun changeCoins(money: Int): Map<Coin, Int> {
        validationMoney(money)
        val coinsMap = getCoinsMap()
        var tempMoney = money
        while (tempMoney > 0){
            val coin = getCoin(tempMoney)
            tempMoney-=coin.getAmount()
            saveCoin(coinsMap, coin)
        }
        return coinsMap
    }

    private fun validationMoney(money: Int) {
        if (money < 0) throw IllegalArgumentException("보유하고 있는 금액은 0보다 커야합니다.")
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