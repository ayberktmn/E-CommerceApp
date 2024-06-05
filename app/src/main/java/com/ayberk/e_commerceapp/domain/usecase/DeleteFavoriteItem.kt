package com.ayberk.e_commerceapp.domain.usecase

import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import javax.inject.Inject

class DeleteFavoriteItem @Inject constructor(
    private val retrofitRepository: RetrofitRep
) {
    suspend operator fun invoke(deleteRocket: FavoriteProducts) {
        retrofitRepository.deletefavoriteitem(deleteRocket)
    }
}