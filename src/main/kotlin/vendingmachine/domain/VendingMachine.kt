package vendingmachine.domain

import vendingmachine.domain.coin.changer.CoinChanger
import vendingmachine.domain.coin.repository.CoinRepository
import vendingmachine.domain.product.repository.ProductRepository
import vendingmachine.excpetion.convertErrorMessage
import vendingmachine.excpetion.multiCatch
import vendingmachine.view.VendingMachineView
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import kotlin.IllegalStateException

class VendingMachine(private val vendingMachineView: VendingMachineView,
                     private val coinChanger: CoinChanger,
                     private val coinRepository: CoinRepository,
                     private val productRepository: ProductRepository) {

    fun run(){
        initMachine()
        setProducts()
        userInteraction()
    }

    private fun initMachine() {
        execute{
            val remainMoney = vendingMachineView.inputVendingMachineRemainMoney()
            coinRepository.setCoins(coinChanger.changeCoins(remainMoney))
            vendingMachineView.printVendingMachineRemainCoin(coinRepository.getRemainCoins())
        }
    }

    private fun setProducts(){
        execute {
            productRepository.setProducts(vendingMachineView.inputVendingMachineProducts())
        }
    }

    private fun userInteraction(){
        var inputMoney = vendingMachineView.inputMoney()
        execute {
            while (true){
                vendingMachineView.printInputMoney(inputMoney)
                if(productRepository.validationPurchaseProducts(inputMoney).not())
                    exitMachine(inputMoney)
                val productName = vendingMachineView.inputPurchaseProductName()
                inputMoney = productRepository.purchase(inputMoney,productName)
            }
        }
    }

    private fun exitMachine(inputMoney: Int) {
        val returnCoins = coinRepository.returnCoins(inputMoney)
        vendingMachineView.printExitMessage(returnCoins)
    }

    private fun execute(retry : Boolean = true, block : () -> Unit){
        block.multiCatch(IllegalArgumentException::class, IllegalStateException::class, NumberFormatException::class){
            println(convertErrorMessage(it.message?:"Something Problem"))
            if(retry)
                execute(true,block)
        }
    }
}