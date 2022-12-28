package vendingmachine

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import vendingmachine.domain.VendingMachine
import vendingmachine.domain.coin.model.ChangeCoin
import vendingmachine.domain.coin.model.Coin

class VendingMachineTest {

    @Test
    fun `잔돈 반환 Test`(){
        val remainMoney = 500
        val remainCoins = mapOf(Coin.COIN_100 to 5)
        val list = VendingMachine.getRemainCoins(remainCoins, remainMoney)
        Assertions.assertThat(list[0]).isEqualTo(ChangeCoin(Coin.COIN_100,5))
    }
}