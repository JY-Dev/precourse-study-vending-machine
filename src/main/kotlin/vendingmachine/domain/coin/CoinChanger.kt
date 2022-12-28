package vendingmachine.domain.coin

import vendingmachine.domain.coin.model.ChangeCoin

/**
 * 금액을 Coin으로 반환하기 위한 interface
 */
interface CoinChanger {
    /**
     * @param money : 변경하고자 하는 금액
     * 입력받은 금액을 Coin으로 변경하는 함수
     * @return Coin과 Coin 갯수를 담고있는 ChangeCoin
     */
    fun changeCoins(money : Int) : List<ChangeCoin>
}