package com.ayberk.e_commerceapp.domain.retrofit

import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.data.source.FavoritesRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitRep @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceIns,
    private val spaceRoomDB: FavoritesRoomDB,
) {
    suspend fun getProducts(): Resource<List<Products>> {
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

    suspend fun upsertProduct(products: FavoriteProducts) {
        withContext(Dispatchers.IO) {
            spaceRoomDB.productFavoriteDAO().insertFavorite(products)
        }
    }

    suspend fun clearRoom() {
        withContext(Dispatchers.IO) {
            spaceRoomDB.productFavoriteDAO().clearBags()
        }
    }

    suspend fun deletefavoriteitem(deleteRocket: FavoriteProducts) {
        withContext(Dispatchers.IO) {
            spaceRoomDB.productFavoriteDAO().deleteFavorite(deleteRocket)
        }
    }
}