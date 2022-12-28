package vendingmachine.view

import camp.nextstep.edu.missionutils.Console
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.product.convertor.ProductMessageResolver
import vendingmachine.product.model.Product
import java.lang.StringBuilder

class NormalVendingMachineView(private val productMessageResolver: ProductMessageResolver) : VendingMachineView {
    override fun inputVendingMachineRemainMoney(): Int {
        println("자판기가 보유하고 있는 금액을 입력해 주세요.")
        return Console.readLine().toInt()
    }

    override fun printVendingMachineRemainCoin(changeCoins: List<ChangeCoin>) {
        val st = StringBuilder().append("\n")
        changeCoins.forEach {
            st.append("${it.coin.getAmount()}원 - ${it.count}개\n")
        }
        println(st)
    }

    override fun inputVendingMachineProducts(): Map<Product, Int> {
        println("상품명과 가격, 수량을 입력해 주세요.")
        val message = Console.readLine()
        return productMessageResolver.resolve(message)
    }

}