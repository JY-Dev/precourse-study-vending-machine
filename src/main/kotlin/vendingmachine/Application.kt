package vendingmachine

import vendingmachine.domain.VendingMachine
import vendingmachine.domain.coin.changer.RandomCoinChanger
import vendingmachine.domain.product.convertor.NormalProductMessageResolver
import vendingmachine.view.NormalVendingMachineView

fun main() {
    val productMessageResolver = NormalProductMessageResolver()
    val vendingMachineView = NormalVendingMachineView(productMessageResolver)
    val coinChanger = RandomCoinChanger()
    val vendingMachine = VendingMachine(vendingMachineView,coinChanger)

}
