package com.ayberk.e_commerceapp.domain.usecase


import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSaleProductsUseCase @Inject constructor(
    private val productsRepository: RetrofitRep
) {
    suspend operator fun invoke(): Resource<List<Products>> {
        return try {
            Resource.Loading
            val result = productsRepository.getProducts()
            result
        } catch (e: HttpException) {
            Resource.Error(e.message())
        } catch (e: IOException) {
            Resource.Error(e.message.toString())
        }
    }
}