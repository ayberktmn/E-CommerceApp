package com.ayberk.e_commerceapp.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.common.showSnackbar
import com.ayberk.e_commerceapp.databinding.FragmentHomeBinding
import com.ayberk.e_commerceapp.databinding.FragmentProfileBinding
import com.ayberk.e_commerceapp.presentation.viewmodel.ProductsViewModel
import com.ayberk.e_commerceapp.presentation.viewmodel.ProfileViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val productViewModel: ProductsViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()



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

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        val dialogTitle = "Şifre Değişikliği : "

        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle(dialogTitle + currentUserEmail)

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
}
