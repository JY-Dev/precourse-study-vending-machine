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
        updateProduct()
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

    private fun updateProduct(){
        var inputMoney = vendingMachineView.inputMoney()
        execute {
            while (true){
                vendingMachineView.printInputMoney(inputMoney)
                products.validationPurchase(inputMoney)
                inputMoney = purchase(inputMoney)
            }
        }
    }

    private fun purchase(inputMoney: Int): Int {
        val productName = vendingMachineView.inputPurchaseProductName()
        val product = products.findProductByName(productName)
        return updateProduct(product, inputMoney)
    }

    private fun updateProduct(product: Product, inputMoney: Int): Int {
        val mutableMap = products.toMutableMap()
        val productCnt = mutableMap[product] ?: 1
        mutableMap[product] = productCnt.minus(1)
        products = mutableMap
        return inputMoney-product.price
    }

    private fun Product.isPossiblePurchase(money : Int) : Boolean =
        this.price <= money

    private fun Map<Product, Int>.findProductByName(productName : String) : Product{
        return this.keys.find { it.name == productName } ?: throw IllegalArgumentException("등록 되지 않은 상품 입니다.")
    }

    private fun Map<Product,Int>.validationPurchase(inputMoney: Int){
        this.validationEmptyProduct(inputMoney)
        val productCnt = this.count { it.value > 0 && it.key.isPossiblePurchase(inputMoney) }
        if(productCnt == 0)
            exitMachine(inputMoney)
    }
    private fun Map<Product, Int>.validationEmptyProduct(
        inputMoney: Int
    ) {
        if (this.getRemainProducts().isEmpty()) {
            exitMachine(inputMoney)
        }
    }

    private fun exitMachine(inputMoney: Int) {
        val returnCoins = coins.returnCoins(inputMoney)
        vendingMachineView.printExitMessage(returnCoins)
    }

    private fun Map<Coin,Int>.returnCoins(remainMoney : Int) : List<ChangeCoin>{
        val mutableMap = this.toMutableMap()
        val result = getRemainCoins(this, remainMoney){ coin, amount ->
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