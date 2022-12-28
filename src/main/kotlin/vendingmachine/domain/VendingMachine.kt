package vendingmachine.domain

import vendingmachine.domain.coin.changer.CoinChanger
import vendingmachine.domain.coin.mapper.toChangeCoinList
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin
import vendingmachine.domain.product.model.Product
import vendingmachine.excpetion.convertErrorMessage
import vendingmachine.excpetion.multiCatch
import vendingmachine.view.VendingMachineView
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import kotlin.IllegalStateException

class VendingMachine(private val vendingMachineView: VendingMachineView,
                     private val coinChanger: CoinChanger) {

    private lateinit var coins : Map<Coin,Int>
    private lateinit var products : Map<Product,Int>

    fun start(){
        initMachine()
        setProducts()
        purchaseProduct()
    }

    private fun initMachine() {
        execute{
            val remainMoney = vendingMachineView.inputVendingMachineRemainMoney()
            coins = coinChanger.changeCoins(remainMoney)
            vendingMachineView.printVendingMachineRemainCoin(coins.toChangeCoinList())
        }
    }

    private fun setProducts(){
        execute {
            products = vendingMachineView.inputVendingMachineProducts()
        }
    }

    private fun purchaseProduct(){
        val inputMoney = vendingMachineView.inputMoney()
        val remainProducts = products.getRemainProducts()
        validationPurchase(remainProducts, inputMoney)
    }

    private fun validationPurchase(
        remainProducts: Map<Product, Int>,
        inputMoney: Int
    ) {
        if (remainProducts.isEmpty()) {
            val returnCoins = coins.returnCoins(inputMoney)
            vendingMachineView.printExitMessage(returnCoins)
        }
    }

    private fun Map<Coin,Int>.returnCoins(remainMoney : Int) : List<ChangeCoin>{
        val result = mutableListOf<ChangeCoin>()
        val mutableMap = this.toMutableMap()
        getRemainCoins(this, remainMoney){ coin, amount ->
            mutableMap[coin] = amount
        }
        coins = mutableMap
        return result
    }

    private fun Map<Product,Int>.getRemainProducts() : Map<Product,Int>{
        return this.filter { it.value > 0 }
    }

    private fun execute(retry : Boolean = true, block : () -> Unit){
        block.multiCatch(IllegalArgumentException::class, IllegalStateException::class, NumberFormatException::class){
            println(convertErrorMessage(it.message?:"Something Problem"))
            if(retry)
                execute(true,block)
        }
    }

    companion object{
        fun getRemainCoins(
            remainCoins: Map<Coin, Int>,
            remainMoney: Int,
            updateCoin : ((coin : Coin, amount : Int) -> Unit)? = null
        ) : List<ChangeCoin> {
            val result = mutableListOf<ChangeCoin>()
            var tempRemainMoney = remainMoney
            remainCoins.forEach { (coin, amount) ->
                var remainCoinCnt = amount
                val coinAmount = coin.getAmount()
                while (remainCoinCnt > 0 && tempRemainMoney >= coinAmount) {
                    tempRemainMoney -= coinAmount
                    remainCoinCnt--
                }
                if (amount > remainCoinCnt) {
                    updateCoin?.invoke(coin,remainCoinCnt)
                    val changeCoin = ChangeCoin(coin, amount - remainCoinCnt)
                    result.add(changeCoin)
                }
            }
            return result
        }
    }
}