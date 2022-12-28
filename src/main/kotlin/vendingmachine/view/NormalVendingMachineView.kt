package vendingmachine.view

import camp.nextstep.edu.missionutils.Console
import vendingmachine.domain.coin.model.ChangeCoin
import java.lang.StringBuilder

class NormalVendingMachineView : VendingMachineView {
    override fun inputVendingMachineRemainMoney(): Int {
        println("자판기가 보유하고 있는 금액을 입력해 주세요.")
        return Console.readLine().toInt()
    }

    override fun printVendingMachineRemainCoin(changeCoins: List<ChangeCoin>) {
        val st = StringBuilder()
        changeCoins.forEach {
            st.append("${it.coin.getAmount()}원 - ${it.count}개\n")
        }
    }

}