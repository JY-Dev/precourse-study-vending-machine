package vendingmachine.coin

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import vendingmachine.domain.coin.changer.CoinChanger
import vendingmachine.domain.coin.changer.RandomCoinChanger
import vendingmachine.domain.coin.model.Coin

class RandomCoinChangerTest {
    private val coinChanger : CoinChanger = RandomCoinChanger()
    @Test
    fun `CoinChanger로 반환된 Map의 size는 Coin종류 size이어야 한다`(){
        //Given
        val money = 1000
        // When
        val changeCoins = coinChanger.changeCoins(money)
        // Then
        Assertions.assertThat(changeCoins.size).isEqualTo(Coin.values().size)
    }

    @Test
    fun `주어진 돈보다 Coin의 가치가 높은 경우 RandomCoinChanger는 가치가 높은 코인에 대해 반환하지 않아야 한다`(){
        //Given
        val money = 300
        // When
        val changeCoins = coinChanger.changeCoins(money)
        // Then
        Assertions.assertThat(changeCoins[Coin.COIN_500]).isEqualTo(0)
    }

    @Test
    fun `주어진 돈 만큼 Coin를 반환해줘야 한다`(){
        //Given
        val money = 500
        // When
        val changeCoins = coinChanger.changeCoins(money)
        // Then
        val result = changeCoins.entries.sumOf { it.key.getAmount()*it.value }

        Assertions.assertThat(result).isEqualTo(money)

    }
}