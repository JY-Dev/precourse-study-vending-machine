package vendingmachine.domain

import vendingmachine.domain.coin.changer.CoinChanger
import vendingmachine.domain.coin.mapper.toChangeCoinList
import vendingmachine.domain.coin.model.Coin
import vendingmachine.excpetion.convertExceptionMessage
import vendingmachine.excpetion.multiCatch
import vendingmachine.view.VendingMachineView
import java.lang.IllegalArgumentException
import kotlin.IllegalStateException

class VendingMachine(private val vendingMachineView: VendingMachineView,
                     private val coinChanger: CoinChanger) {

    private lateinit var remainCoins : Map<Coin,Int>

    init {
        initMachine()
    }

    private fun initMachine() {
        retryFail{
            val remainMoney = vendingMachineView.inputVendingMachineRemainMoney()
            remainCoins = coinChanger.changeCoins(remainMoney)
            vendingMachineView.printVendingMachineRemainCoin(remainCoins.toChangeCoinList())
        }
    }


    private fun retryFail(block : () -> Unit){
        block.multiCatch(IllegalArgumentException::class, IllegalStateException::class){
            println(convertExceptionMessage(it.message?:"Something Problem"))
            retryFail(block)
        }
    }
}