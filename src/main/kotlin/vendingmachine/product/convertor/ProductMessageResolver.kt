package vendingmachine.product.convertor

import vendingmachine.product.model.Product

interface ProductMessageResolver {
    fun resolve(productMessage : String) : Map<Product,Int>
}