package vendingmachine.domain.coin.changer

import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin

class RandomCoinChanger : CoinChanger{
    override fun changeCoins(money: Int): Map<Coin, Int> {
        return mapOf()
    }

}