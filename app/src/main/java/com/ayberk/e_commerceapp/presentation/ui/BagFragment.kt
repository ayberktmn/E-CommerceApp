package com.ayberk.e_commerceapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.databinding.FragmentBagBinding
import com.ayberk.e_commerceapp.databinding.FragmentHomeBinding

class BagFragment : Fragment() {

    private lateinit var binding: FragmentBagBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}