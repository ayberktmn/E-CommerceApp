package com.ayberk.e_commerceapp.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.common.showSnackbar
import com.ayberk.e_commerceapp.databinding.FragmentHomeBinding
import com.ayberk.e_commerceapp.databinding.FragmentProfileBinding
import com.ayberk.e_commerceapp.presentation.viewmodel.ProductsViewModel
import com.ayberk.e_commerceapp.presentation.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val productViewModel: ProductsViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtPasswordChange.setOnClickListener {
            showChangePasswordDialog()
        }

        photoGmailUser()
        signOut()

        with(binding) {
            with(productViewModel) {
                user.observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Success -> {
                            txtProfileEmail.text = it.data.email
                            txtProfileEmail.text = it.data.emailRegister
                        }

                        is Resource.Error -> {
                            requireView().showSnackbar(it.throwable.toString())
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.passwordchange, null)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle(currentUser?.email)

        val alertDialog = alertDialogBuilder.create()

        dialogView.findViewById<View>(R.id.todoNextBtn).setOnClickListener {
            val currentPassword = dialogView.findViewById<TextInputEditText>(R.id.PasswordChange1).text.toString()
            val newPassword = dialogView.findViewById<TextInputEditText>(R.id.PasswordChange).text.toString()

            if (currentPassword.isBlank() || newPassword.isBlank()) {
                requireView().showSnackbar("Lütfen şifre giriniz")
                return@setOnClickListener
            }

            profileViewModel.changePassword(currentPassword, newPassword) { isSuccess, message ->
                if (isSuccess) {
                    requireView().showSnackbar(message ?: "Şifre Başarıyla Güncellendi")
                } else {
                    requireView().showSnackbar(message ?: "Şifre Güncellemesi Başarısız")
                }
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    fun signOut(){
        binding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(context, "Çıkış yapıldı", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profileFragment_to_signInFragment)

            activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.menu?.getItem(0)?.isChecked = true
        }
    }

    fun photoGmailUser(){
        currentUser?.let {
            val photoUrl = it.photoUrl
            photoUrl?.let { url ->
                Glide.with(this)
                    .load(url)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.mavilogo)
                    .transform(CircleCrop())
                    .into(binding.imgProfilePicture)
            }
        }
    }
}


