package com.ayberk.e_commerceapp.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.e_commerceapp.data.source.FavoriteDao
import com.ayberk.e_commerceapp.databinding.FragmentBagBinding
import com.ayberk.e_commerceapp.presentation.adapter.BagAdapter
import com.ayberk.e_commerceapp.presentation.viewmodel.BagViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BagFragment : Fragment() {

    private lateinit var _binding: FragmentBagBinding
    private val binding get() = _binding!!
    private lateinit var BagfavoriteAdapter: BagAdapter

    private val viewModelfav: BagViewModel by viewModels()

    @Inject
    lateinit var bagFavoriteRoomDAO: FavoriteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        }
        viewModelfav.getAllFavoriteRockets()
        setupRecyclerViewBag()
        initObservers()
        deleteFavoriteItem()
    }

    private fun setupRecyclerViewBag() {
        BagfavoriteAdapter = BagAdapter()

        binding.rcyclerBag.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcyclerBag.layoutManager = layoutManager
        binding.rcyclerBag.adapter = BagfavoriteAdapter
    }

    private fun initObservers() {
        viewModelfav.favoriteBagLiveData.observe(viewLifecycleOwner) { favItem ->
            favItem?.let {
                BagfavoriteAdapter.updateList(it)
            }
        }
    }
    fun deleteFavoriteItem(){
        BagfavoriteAdapter.onDeleteClickListener = { favoriteItem ->
            viewModelfav.deleteFavItem(favoriteItem)
            BagfavoriteAdapter.updateList(BagfavoriteAdapter.bagfavoriteList?.filter { it != favoriteItem } ?: emptyList())

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.root
    }
}