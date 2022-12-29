package vendingmachine.domain.product.repository

import vendingmachine.domain.product.model.Product
import java.lang.IllegalArgumentException

class NormalProductRepository : ProductRepository {
    private var products : Map<Product,Int> = mapOf()

    override fun purchase(money: Int, productName: String) : Int{
        val product = findProductByName(productName)
        validationPurchaseProduct(product,money)
        updateProduct(product,money)
        return money-product.price
    }

    override fun findProductByName(productName: String) : Product {
        return products.keys.find { it.name == productName } ?: throw IllegalArgumentException("등록 되지 않은 상품 입니다.")
    }

    override fun setProducts(products: Map<Product, Int>) {
        this.products = products
    }

    override fun updateProduct(product: Product, inputMoney: Int) {
        val mutableMap = products.toMutableMap()
        val productCnt = mutableMap[product] ?: 1
        mutableMap[product] = productCnt.minus(1)
        products = mutableMap
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