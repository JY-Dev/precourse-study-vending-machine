package vendingmachine.coin

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import vendingmachine.domain.coin.model.Coin

class CoinTest {

    @Test
    fun `getCoinsAmount의 반환 리스트의 size는 Coin종류의 Size랑 같아야 한다`(){
        Assertions.assertThat(Coin.getCoinsAmount().size).isEqualTo(Coin.values().size)
    }

    @Test
    fun `getCoinsAmount predicate를 적용한 return 값은 조건에 맞게 필터링 되야 한다`(){
        Assertions.assertThat(Coin.getCoinsAmount { it > 500 }.size).isEqualTo(0)
    }
}