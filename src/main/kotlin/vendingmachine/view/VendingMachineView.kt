package vendingmachine.view

import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.product.model.Product

interface VendingMachineView {
    fun inputVendingMachineRemainMoney() : Int
    fun inputVendingMachineProducts() : Map<Product,Int>
    fun inputMoney() : Int


    fun printVendingMachineRemainCoin(changeCoins : List<ChangeCoin>)

    fun printExitMessage(returnCoins : List<ChangeCoin>)

    fun printInputMoney(money : Int)

}