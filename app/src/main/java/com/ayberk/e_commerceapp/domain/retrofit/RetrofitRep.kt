package com.ayberk.e_commerceapp.domain.retrofit

import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.data.model.products
import javax.inject.Inject

class RetrofitRep @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceIns
) {
    suspend fun getProducts(): Resource<List<products>> {
        return try {
            val response = retrofitServiceInstance.getProducts()
            if (response.isSuccessful) {
                val products = response.body()?.products ?: emptyList()
                Resource.Success(products)
            } else {
                Resource.Fail("HTTP error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}