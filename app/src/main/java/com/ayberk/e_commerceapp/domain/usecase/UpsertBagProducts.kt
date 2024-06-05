package com.ayberk.e_commerceapp.domain.usecase

import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep

class UpsertBagProducts(
    private val retrofitRepository: RetrofitRep
) {
    suspend operator fun invoke(products: FavoriteProducts) {
        retrofitRepository.upsertProduct(products)
    }

}