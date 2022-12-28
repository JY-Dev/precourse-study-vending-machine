package vendingmachine.domain.coin.changer

import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin

/**
 * 금액을 Coin으로 반환하기 위한 interface
 */
interface CoinChanger {
    /**
     * @param money : 변경하고자 하는 금액
     * 입력받은 금액을 Coin으로 변경하는 함수
     * @return Coin과 Coin 갯수를 담고있는 Map
     */
    fun changeCoins(money : Int) : Map<Coin,Int>
}