package vendingmachine.view

import vendingmachine.domain.coin.model.ChangeCoin

interface VendingMachineView {
    fun inputVendingMachineRemainMoney() : Int
    fun printVendingMachineRemainCoin(changeCoins : List<ChangeCoin>)
}