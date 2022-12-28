package vendingmachine.product

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import vendingmachine.product.convertor.NormalProductMessageResolver
import vendingmachine.product.model.Product
import java.lang.IllegalArgumentException

class NormalProductMessageResolverTest {
    private val productMessageResolver = NormalProductMessageResolver()

    @Test
    @DisplayName("[콜라,1500,20];[사이다,1000,10]가 주어졌을 때 정상적으로 Product로 반환하는지 테스트")
    fun normalProductMessageResolverTest(){
        val message = "[콜라,1500,20];[사이다,1000,10]"
        val target = listOf(Product("콜라",1500) to 20,Product("사이다",1000) to 10).sortedBy { it.first.name }
        val result = productMessageResolver.resolve(message).toSortedMap(compareBy { it.name }).toList()
        Assertions.assertThat(result).isEqualTo(target)
    }

    @Test
    @DisplayName("잘못된 메세지가 주어졌을 때 Exception 발생하는지 테스트")
    fun normalProductMessageResolverExceptionTest(){
        val message = "error message"
        assertThrows<IllegalArgumentException> { productMessageResolver.resolve(message) }
    }
}