package com.ayberk.e_commerceapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.data.User.User
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import com.ayberk.e_commerceapp.domain.usecase.BagUseCase
import com.ayberk.e_commerceapp.domain.usecase.GetCurrentUserUseCase
import com.ayberk.e_commerceapp.domain.usecase.GetSaleProductsUseCase
import com.ayberk.e_commerceapp.domain.usecase.event.BagEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val retrofitRep: RetrofitRep,
    private val getSaleProductsUseCase: GetSaleProductsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val bagUseCase: BagUseCase
) : ViewModel() {

    private val _user = MutableLiveData<Resource<User>>(Resource.Loading)
    val user: LiveData<Resource<User>> = _user

    private val _productsState = MutableLiveData<ProductState>()
    val productState: LiveData<ProductState> get() = _productsState

    private val _saleProducts = MutableLiveData<Resource<List<Products>>>(Resource.Loading)
    val saleProducts: LiveData<Resource<List<Products>>> = _saleProducts

    private val _productAddedToCart = MutableLiveData<Boolean>()
    val productAddedToCart: LiveData<Boolean> get() = _productAddedToCart

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

    private suspend fun upsertRockets(products: FavoriteProducts) {
        bagUseCase.upsertDeleteBag(products = products)
    }

    fun onEvent(event: BagEvent) {
        when (event) {
            is BagEvent.UpsertDeleteBag -> {
                viewModelScope.launch {
                    try {
                        upsertRockets(event.bag)
                        _productAddedToCart.postValue(true)
                    } catch (e: Exception) {
                        // Hata durumunda Exception'ı kaydedin veya işlemi uygun şekilde ele alın
                    }
                }
            }
        }
    }
}

data class ProductState(
    val isLoading: Boolean = false,
    val productsList: List<Products> = emptyList(),
    val errorMessage: String? = null
)
