package com.ayberk.e_commerceapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.data.User.User
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import com.ayberk.e_commerceapp.domain.usecase.GetCurrentUserUseCase
import com.ayberk.e_commerceapp.domain.usecase.GetSaleProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val retrofitRep: RetrofitRep,
    private val getSaleProductsUseCase: GetSaleProductsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<Resource<User>>(Resource.Loading)
    val user: LiveData<Resource<User>> = _user

    private val _productsState = MutableLiveData<ProductState>()
    val productState : LiveData<ProductState> get() = _productsState

    private val _saleProducts = MutableLiveData<Resource<List<Products>>>(Resource.Loading)
    val saleProducts: LiveData<Resource<List<Products>>> = _saleProducts

    init {
        viewModelScope.launch {
            _user.value = getCurrentUserUseCase()!!
           _saleProducts.value = getSaleProductsUseCase()!!
        }
    }

    fun getProducts() {
        _productsState.value = ProductState(isLoading = true)
        viewModelScope.launch {
            when (val response = retrofitRep.getProducts()) {
                is Resource.Success<*> -> {
                    val products = response.data
                    if (products != null) {
                        _productsState.value = ProductState(
                            isLoading = false,
                            productsList = products as List<Products>
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
    val productsList: List<Products> = emptyList(),
    val errorMessage: String? = null
)
