package vendingmachine.coin

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import vendingmachine.domain.coin.model.Coin
import java.lang.IllegalArgumentException

class CoinTest {

    @Test
    fun `getCoinsAmount의 반환 리스트의 size는 Coin종류의 Size랑 같아야 한다`(){
        Assertions.assertThat(Coin.getCoinsAmount().size).isEqualTo(Coin.values().size)
    }

    @Test
    fun `getCoinsAmount predicate를 적용한 return 값은 조건에 맞게 필터링 되야 한다`(){
        Assertions.assertThat(Coin.getCoinsAmount { it > 500 }.size).isEqualTo(0)
    }

    @Test
    fun `amountToCoin의 결과 값은 Coin amount에 따른 Coin를 반환해야한다`(){
        val amount = 100
        Assertions.assertThat(Coin.amountToCoin(amount)).isEqualTo(Coin.COIN_100)
    }

    @Test
    fun `amountToCoin의 결과 값은 Coin amount맞지 않으면 IllegalArgumentException이 발생한다`(){
        val amount = 222
        assertThrows<IllegalArgumentException> { Coin.amountToCoin(amount) }
    }
}