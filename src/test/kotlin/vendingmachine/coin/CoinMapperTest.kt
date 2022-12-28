package vendingmachine.coin

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import vendingmachine.domain.coin.mapper.toChangeCoinList
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin

class CoinMapperTest {

    @Test
    fun `Map (Coin,Int) toChangeCoinList 테스트`(){
        val target = listOf(ChangeCoin(Coin.COIN_50,5))
        val result = hashMapOf<Coin,Int>().apply {
            this[Coin.COIN_50] = 5
        }.toChangeCoinList()
        Assertions.assertThat(result).isEqualTo(target)
    }
}