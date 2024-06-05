package com.ayberk.e_commerceapp.domain.usecase

import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import javax.inject.Inject


class ClearRoom @Inject constructor(
    private val retrofitRepository: RetrofitRep
) {
    suspend operator fun invoke() {
        retrofitRepository.clearRoom()
    }
}