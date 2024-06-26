package com.ayberk.e_commerceapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.data.User.User
import com.ayberk.e_commerceapp.databinding.FragmentSignUpBinding
import com.ayberk.e_commerceapp.presentation.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {

    private val viewModel by viewModels<SignUpViewModel>()
    private lateinit var navController : NavController
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        binding.textViewSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            val verifyPass = binding.verifyPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                if (pass == verifyPass) {
                    binding.progressBarSignUp.visibility = View.VISIBLE
                    register(email, pass)
                } else {
                    Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.showEmailExistsWarning.observe(viewLifecycleOwner) { showWarning ->
            if (showWarning) {
                binding.progressBarSignUp.visibility = View.GONE
                Toast.makeText(requireContext(), "An account already exists with this email.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.registrationSuccessful.observe(viewLifecycleOwner) { isSuccessful ->
            if (isSuccessful) {
                binding.progressBarSignUp.visibility = View.GONE
                Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_signUpFragment_to_homeFragment)
            }
        }


    }

    private fun register(email: String, password: String) {
        val user = User(email)
        viewModel.createAccount(user, password)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}