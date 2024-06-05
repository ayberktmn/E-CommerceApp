package com.ayberk.e_commerceapp.domain.usecase.event

import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products

sealed class BagEvent {

    data class UpsertDeleteBag(val bag: FavoriteProducts) : BagEvent()

}