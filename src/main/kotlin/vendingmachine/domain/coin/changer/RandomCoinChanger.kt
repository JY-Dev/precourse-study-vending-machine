package vendingmachine.domain.coin.changer

import vendingmachine.domain.coin.model.ChangeCoin

class RandomCoinChanger : CoinChanger{
    override fun changeCoins(money: Int): List<ChangeCoin> {

        return emptyList()
    }
}