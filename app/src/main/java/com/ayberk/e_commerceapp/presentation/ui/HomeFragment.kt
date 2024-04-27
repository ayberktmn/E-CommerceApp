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
import com.ayberk.e_commerceapp.data.model.products
import com.ayberk.e_commerceapp.databinding.FragmentHomeBinding
import com.ayberk.e_commerceapp.presentation.adapter.adapter
import com.ayberk.e_commerceapp.presentation.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var productsAdapter: adapter
    private lateinit var binding: FragmentHomeBinding
    private var isBackPressed = false

    private val productViewModel: ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initObservers()
        productViewModel.getProducts()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            isBackPressed = true
        }

        return binding.root
    }

    private fun initObservers() {
        productViewModel.productState.observe(requireActivity(), Observer { state ->
            state.productsList?.let { productsList ->
                if (productsList.isNotEmpty()) {
                    Toast.makeText(requireContext(), "Ürünler Yükleniyor", Toast.LENGTH_SHORT).show()
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

    private fun setupRecyclerView(productsList: List<products>) {
        binding.rcrylerProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        productsAdapter = adapter()

        productsAdapter.setProductsList(productsList)

        binding.rcrylerProducts.adapter = productsAdapter
    }
}