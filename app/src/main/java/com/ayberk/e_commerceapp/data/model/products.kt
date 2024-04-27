package com.ayberk.e_commerceapp.data.model


data class products(
    val id: Int,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val description: String,
    val category: String,
    val imageOne: String,
    val imageTwo: String,
    val imageThree: String,
    val rate: Double,
    val count: Int,
    val saleState: Boolean
)

data class ProductsResponse(
    val products: List<products>,
    val status: Int,
    val message: String
)