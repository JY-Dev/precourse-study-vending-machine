package vendingmachine

import vendingmachine.domain.VendingMachine
import vendingmachine.domain.coin.changer.RandomCoinChanger
import vendingmachine.view.NormalVendingMachineView

fun main() {
    val vendingMachine = VendingMachine(NormalVendingMachineView(),RandomCoinChanger())

}
