package vendingmachine.domain.coin.model

import vendingmachine.excpetion.convertExceptionMessage
import java.lang.IllegalArgumentException

enum class Coin(private val amount: Int) {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    companion object{
        fun getCoinsAmount() : List<Int>{
            return values().map { it.amount }
        }
        fun getCoinsAmount(predicate : (amount : Int) -> Boolean) : List<Int>{
            return values().map { it.amount }.filter(predicate)
        }
        fun amountToCoin(amount : Int) : Coin{
            return values().find { it.amount == amount } ?:
                throw IllegalArgumentException(convertExceptionMessage("Amount에 맞는 Coin이 없습니다."))
        }
    }
}