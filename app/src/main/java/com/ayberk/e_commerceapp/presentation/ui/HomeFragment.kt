package com.ayberk.e_commerceapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.common.gone
import com.ayberk.e_commerceapp.common.showSnackbar
import com.ayberk.e_commerceapp.common.visible
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.data.source.FavoriteDao
import com.ayberk.e_commerceapp.databinding.FragmentHomeBinding
import com.ayberk.e_commerceapp.domain.usecase.event.BagEvent
import com.ayberk.e_commerceapp.presentation.adapter.ProductsAdapter
import com.ayberk.e_commerceapp.presentation.adapter.SaleProductsAdapter
import com.ayberk.e_commerceapp.presentation.viewmodel.ProductsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var binding: FragmentHomeBinding
    private var isBackPressed = false

    private val productViewModel: ProductsViewModel by viewModels()

    private val saleProductsAdapter by lazy { SaleProductsAdapter() }

    @Inject
    lateinit var favoriteDao: FavoriteDao


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            isBackPressed = true
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        productViewModel.getProducts()

    }

    private fun initObservers() {

        with(binding) {
            with(productViewModel) {

                user.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            txtName.text = it.data.email
                            txtName.text = it.data.emailRegister
                        }

                        is Resource.Error -> {
                            requireView().showSnackbar(it.throwable.toString())
                        }

                        else -> {}
                    }
                }

                saleProducts.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            progressbar.gone()
                            saleProductsAdapter.updateList(it.data)
                            val compositePageTransformer = CompositePageTransformer()
                            compositePageTransformer.addTransformer { page, position ->
                                val r = 1 - abs(position)
                                page.scaleY = (0.85f + r * 0.15f)
                            }

                            viewpagerSale.apply {
                                adapter = saleProductsAdapter
                                clipToPadding = false
                                clipChildren = false
                                offscreenPageLimit = 3
                                viewpagerSale.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                                setPageTransformer(compositePageTransformer)
                                currentItem = 1
                            }
                        }

                        is Resource.Error -> {
                            progressbar.gone()
                            requireView().showSnackbar(it.throwable.toString())
                        }
                        Resource.Loading -> progressbar.visible()
                        else -> {}
                    }
                }
            }
        }

        productViewModel.productState.observe(requireActivity(), Observer { state ->
            state.productsList.let { productsList ->
                if (productsList.isNotEmpty()) {
                    setupRecyclerView(productsList)
                } else {
                    println("Ürün listesi boş veya null.")
                }
            }
            state.errorMessage?.let {
                println("RecyclerView hatası: $it")
            }
        })
    }

    private fun setupRecyclerView(productsList: List<Products>) {
        binding.rcrylerProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        productsAdapter = ProductsAdapter(
            event = { bagEvent ->
                val result = productViewModel.onEvent(bagEvent)
                productViewModel.productAddedToCart.observe(viewLifecycleOwner) { addedToCart ->
                    if (addedToCart) {
                        Toast.makeText(requireContext(), "Ürün sepete eklendi", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Ürün sepete eklenemedi!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            dataDao = favoriteDao
        )

        productsAdapter.setProductsList(productsList)

        binding.rcrylerProducts.adapter = productsAdapter
        binding.rcrylerProducts.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root
    }
}
