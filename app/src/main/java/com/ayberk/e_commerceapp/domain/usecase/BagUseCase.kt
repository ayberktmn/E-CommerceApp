package com.ayberk.e_commerceapp.domain.usecase

import com.ayberk.e_commerceapp.domain.usecase.event.BagEvent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
data class BagUseCase @Inject constructor(
    val upsertDeleteBag: UpsertBagProducts,
    val clearRoom: ClearRoom,
    val deleteFavoriteItem: DeleteFavoriteItem,
)