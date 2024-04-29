package com.ayberk.e_commerceapp.domain.retrofit

import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.data.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitServiceIns : Authenticator {
    @GET("get_products")
    suspend fun getProducts(): Response<ProductsResponse>
}