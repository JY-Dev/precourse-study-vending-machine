package vendingmachine

import vendingmachine.domain.VendingMachine
import vendingmachine.domain.coin.changer.RandomCoinChanger
import vendingmachine.domain.coin.repository.NormalCoinRepository
import vendingmachine.domain.product.convertor.NormalProductMessageResolver
import vendingmachine.domain.product.repository.NormalProductRepository
import vendingmachine.view.NormalVendingMachineView

fun main() {
    val productMessageResolver = NormalProductMessageResolver()
    val vendingMachineView = NormalVendingMachineView(productMessageResolver)
    val coinChanger = RandomCoinChanger()
    val coinRepository = NormalCoinRepository()
    val productRepository = NormalProductRepository()
    val vendingMachine = VendingMachine(vendingMachineView,coinChanger,coinRepository, productRepository)
    vendingMachine.run()
}
