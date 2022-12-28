package vendingmachine.domain.product.convertor

import vendingmachine.domain.product.model.Product

interface ProductMessageResolver {
    fun resolve(productMessage : String) : Map<Product,Int>
}