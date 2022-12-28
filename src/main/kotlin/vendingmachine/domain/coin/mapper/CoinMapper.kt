package vendingmachine.domain.coin.mapper

import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin

fun Map<Coin,Int>.toChangeCoinList() : List<ChangeCoin> =
    this.entries.map { it.toChangeCoin() }

private fun Map.Entry<Coin,Int>.toChangeCoin() : ChangeCoin =
    ChangeCoin(this.key,this.value)