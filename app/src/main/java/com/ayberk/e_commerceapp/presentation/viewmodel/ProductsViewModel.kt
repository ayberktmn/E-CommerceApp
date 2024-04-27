package com.ayberk.e_commerceapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import com.ayberk.e_commerceapp.data.model.products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val retrofitRep: RetrofitRep
) : ViewModel() {

    private val _productsState = MutableLiveData<ProductState>()
    val productState : LiveData<ProductState> get() = _productsState

    fun getProducts() {
        _productsState.value = ProductState(isLoading = true)
        viewModelScope.launch {
            when (val response = retrofitRep.getProducts()) {
                is Resource.Success<*> -> {
                    val products = response.data
                    if (products != null) {
                        _productsState.value = ProductState(
                            isLoading = false,
                            productsList = products as List<products>
                        )
                        println("ViewModel data success")
                    } else {
                        _productsState.value = ProductState(
                            isLoading = false,
                            errorMessage = "Data conversion error"
                        )
                        println("Data conversion error")
                    }
                }
                else -> {}
            }
        }
    }
}

data class ProductState(
    val isLoading: Boolean = false,
    val productsList: List<products> = emptyList(),
    val errorMessage: String? = null
)
