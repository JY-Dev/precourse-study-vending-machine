package vendingmachine.domain.coin.model

/**
 *
 * @param coin : Coin 종류
 * @param count : Coin 갯수
 */
data class ChangeCoin(val coin: Coin, val count : Int)