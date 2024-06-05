package com.ayberk.e_commerceapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.source.FavoriteDao
import com.ayberk.e_commerceapp.domain.usecase.BagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val bagRoomDAO: FavoriteDao,
    private val bagUseCases: BagUseCase
) : ViewModel(){

    private val _favoriteBagLiveData = MutableLiveData<List<FavoriteProducts>>()
    val favoriteBagLiveData: LiveData<List<FavoriteProducts>> = _favoriteBagLiveData
    var onFavoriteBagListEmpty: (() -> Unit)? = null

    init {
        getAllFavoriteRockets()
    }

    fun getAllFavoriteRockets() {
        viewModelScope.launch {
            val bagfavorite = withContext(Dispatchers.IO) {
                bagRoomDAO.getFavorites()
            }
            _favoriteBagLiveData.postValue(bagfavorite)
        }
    }

    fun clearRoomIfNotEmpty() {
        if (favoriteBagLiveData.value?.isNotEmpty() == true) {
            viewModelScope.launch {
                bagUseCases.clearRoom()
            }
        } else {
            onFavoriteBagListEmpty?.invoke() // Liste boşsa callback'i çağır
        }
    }

    fun deleteFavItem(favitem: FavoriteProducts) {
        viewModelScope.launch {
            bagUseCases.deleteFavoriteItem(favitem)
        }
    }
}
