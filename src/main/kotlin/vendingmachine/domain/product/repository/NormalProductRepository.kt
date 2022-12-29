package vendingmachine.domain.product.repository

import vendingmachine.domain.product.model.Product
import java.lang.IllegalArgumentException

class NormalProductRepository : ProductRepository {
    private var products : Map<Product,Int> = mapOf()

    override fun purchase(money: Int, productName: String) : Int{
        val product = findProductByName(productName)
        validationPurchaseProduct(product,money)
        consumeProduct(product)
        return money-product.price
    }

    override fun findProductByName(productName: String) : Product {
        return products.keys.find { it.name == productName } ?: throw IllegalArgumentException("등록 되지 않은 상품 입니다.")
    }

    override fun setProducts(products: Map<Product, Int>) {
        products.keys.forEach {
            println(it)
            validationProduct(it)
        }
        this.products = products
    }

    override fun consumeProduct(product: Product) {
        val mutableMap = products.toMutableMap()
        val productCnt = mutableMap[product] ?: 1
        mutableMap[product] = productCnt.minus(1)
        products = mutableMap
    }

    override fun validationProduct(product: Product) {
        if(product.price < 100 || product.price % 10 != 0)
            throw IllegalArgumentException("상품의 설정 가격이 올바르지 않습니다.")
    }

    override fun validationPurchaseProducts(inputMoney: Int) : Boolean{
        val productCnt = products.count { it.value > 0 && it.key.price <= inputMoney}
        return (productCnt > 0)
    }

    override fun validationPurchaseProduct(product: Product, inputMoney: Int){
        if(product.price > inputMoney)
            throw IllegalArgumentException("투입한 금액보다 상품이 더 비쌉니다.")
    }

}