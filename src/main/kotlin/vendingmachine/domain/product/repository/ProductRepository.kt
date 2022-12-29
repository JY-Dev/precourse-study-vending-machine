package vendingmachine.domain.product.repository

import vendingmachine.domain.product.model.Product

interface ProductRepository {
    fun purchase(money : Int, productName: String) : Int
    fun findProductByName(productName : String) : Product
    fun setProducts(products : Map<Product,Int>)
    fun consumeProduct(product: Product)
    fun validationProduct(product: Product)
    fun validationPurchaseProducts(inputMoney: Int) : Boolean
    fun validationPurchaseProduct(product: Product, inputMoney: Int)
}