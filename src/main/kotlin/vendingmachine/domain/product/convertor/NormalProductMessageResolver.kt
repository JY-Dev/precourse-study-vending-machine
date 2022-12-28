package vendingmachine.domain.product.convertor

import vendingmachine.domain.product.model.Product
import java.lang.Exception
import java.lang.IllegalArgumentException

class NormalProductMessageResolver : ProductMessageResolver {
    override fun resolve(productMessage: String): Map<Product, Int> {
        val products = hashMapOf<Product,Int>()
        val productInfoList = getProductInfoList(productMessage)
        try {
            addProduct(productInfoList, products)
        }catch (e : Exception){
            throw IllegalArgumentException("Product String Parsing Error")
        }
        return products
    }

    private fun addProduct(
        productInfoList: List<String>,
        products: HashMap<Product, Int>
    ) {
        productInfoList.forEach {
            val productInfo = it.split(",")
            val product = parseProductString(productInfo)
            val amount = productInfo[2].toInt()
            val productAmount = products[product] ?: 0
            products[product] = productAmount.plus(amount)
        }
    }

    private fun getProductInfoList(productMessage: String) : List<String> {
        return productMessage.replace("[", "")
            .replace("]","")
            .split(";")
    }

    private fun parseProductString(productInfo : List<String>) : Product {
        validation(productInfo)
        val name = productInfo[0]
        val price = productInfo[1].toInt()
        return Product(name,price)
    }

    private fun validation(productInfo: List<String>) {
        if (productInfo.size != 3)
            throw Exception()
    }
}