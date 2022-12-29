package vendingmachine.coin

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin
import vendingmachine.domain.coin.repository.NormalCoinRepository

class CoinRepositoryTest {
    private val coinRepository = NormalCoinRepository()

    @Test
    fun `coin과 coin갯수 돈이 주어졌을 때 가능한 최대의 코인을 반환해야 한다`(){
        val returnCoinCount = coinRepository.getReturnCoinCount(Coin.COIN_100, 5, 500)
        Assertions.assertThat(returnCoinCount).isEqualTo(5)
    }

    @Test
    fun `돈이 주어졌을 때 보유한 코인 만큼 최대한 반환 해줘야 한다`(){
        val coins = mapOf(Coin.COIN_100 to 1,Coin.COIN_50 to 3)
        val money = 250
        val arr = arrayOf(ChangeCoin(Coin.COIN_100,0), ChangeCoin(Coin.COIN_50,0))
        val arr2 = arrayListOf(ChangeCoin(Coin.COIN_100,1), ChangeCoin(Coin.COIN_50,3))
        var idx = 0
        val possibleReturnCoins = coinRepository.getPossibleReturnCoins(coins, money) { coin, amount ->
            Assertions.assertThat(ChangeCoin(coin, amount)).isEqualTo(arr[idx++])
        }
        Assertions.assertThat(possibleReturnCoins).isEqualTo(arr2)
    }
}