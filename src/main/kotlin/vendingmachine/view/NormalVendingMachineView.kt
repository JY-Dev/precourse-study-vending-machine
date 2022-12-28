package vendingmachine.view

import camp.nextstep.edu.missionutils.Console
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.product.convertor.ProductMessageResolver
import vendingmachine.domain.product.model.Product
import java.lang.Exception
import java.lang.StringBuilder

class NormalVendingMachineView(private val productMessageResolver: ProductMessageResolver) : VendingMachineView {
    override fun inputVendingMachineRemainMoney(): Int {
        println("자판기가 보유하고 있는 금액을 입력해 주세요.")
        return Console.readLine().toInt()
    }

    override fun inputVendingMachineProducts(): Map<Product, Int> {
        println("상품명과 가격, 수량을 입력해 주세요.")
        val message = Console.readLine()
        return productMessageResolver.resolve(message)
    }

    override fun inputMoney(): Int {
        println("투입 금액을 입력해 주세요.")
        return Console.readLine().toInt()
    }


    override fun printVendingMachineRemainCoin(changeCoins: List<ChangeCoin>) {
        val st = StringBuilder().append("\n")
        appendCoinInfo(changeCoins, st)
        println(st)
    }

    override fun printExitMessage(returnCoins : List<ChangeCoin>) {
        val st = StringBuilder().append("잔돈\n")
        appendCoinInfo(returnCoins, st)
        println(st)
        throw Exception("Exit Program")
    }

    override fun printInputMoney(money : Int) {
        println("투입 금액: ${money}원")
    }

    private fun appendCoinInfo(
        returnCoins: List<ChangeCoin>,
        st: StringBuilder
    ) {
        returnCoins.forEach {
            st.append("${it.coin.getAmount()}원 - ${it.count}개\n")
        }
    }

}