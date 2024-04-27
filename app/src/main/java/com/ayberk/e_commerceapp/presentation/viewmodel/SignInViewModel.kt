package com.ayberk.e_commerceapp.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayberk.e_commerceapp.data.User.User
import com.ayberk.e_commerceapp.domain.retrofit.RetrofitRep
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val retroRepository: RetrofitRep,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _googleSignInResult = MutableLiveData<Pair<String, String?>>()
    val googleSignInResult: LiveData<Pair<String, String?>>
        get() = _googleSignInResult
    fun signinEmail(user: User, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(user.email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Giriş başarılı oldu
                onResult(true)
            } else {
                // Giriş başarısız oldu
                onResult(false)
            }
        }
    }

    fun handleGoogleSignInResult(data: Intent?) {
        if (data == null) {
            _googleSignInResult.value = Pair("", null)
            return
        }
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val googleEmail = account.email
                val googleProfilePicURL = account.photoUrl.toString()
                _googleSignInResult.value = Pair(googleEmail ?: "", googleProfilePicURL)
            } else {
                _googleSignInResult.value = Pair("", null)
            }
        } catch (e: ApiException) {
            _googleSignInResult.value = Pair("", null)
        }
    }

    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Giriş başarılı
                } else {
                    // Hata oluştu
                }
            }
    }
}