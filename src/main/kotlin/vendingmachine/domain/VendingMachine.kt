package vendingmachine.domain

import vendingmachine.domain.coin.changer.CoinChanger
import vendingmachine.domain.coin.mapper.toChangeCoinList
import vendingmachine.domain.coin.model.Coin
import vendingmachine.excpetion.convertExceptionMessage
import vendingmachine.view.VendingMachineView
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import java.util.IllegalFormatCodePointException

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
        try {
            block()
        }catch (e : Exception){
            when(e){
                is IllegalStateException,
                is IllegalArgumentException -> {
                    println(convertExceptionMessage(e.message?:"Something Problem"))
                    retryFail{
                        block()
                    }
                }
            }
        }
    }
}