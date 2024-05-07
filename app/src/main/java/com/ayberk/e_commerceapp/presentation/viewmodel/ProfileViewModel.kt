package com.ayberk.e_commerceapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {

    fun changePassword(currentPassword: String, newPassword: String, onComplete: (Boolean, String?) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val email = user.email
            println("email : ${email}")

            if (email != null) {
                val credential = EmailAuthProvider.getCredential(email, currentPassword)

                user.reauthenticate(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            // Yeniden doğrulama başarılı ise, yeni şifreyi ayarla
                            user.updatePassword(newPassword)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        // Şifre güncelleme başarılı
                                        onComplete(true, "Şifre Değişikliği Başarılı")
                                    } else {
                                        // Şifre güncelleme başarısız
                                        onComplete(false, "Şifre Değişikliği Başarısız")
                                    }
                                }
                        } else {
                            // Doğrulama başarısız
                            println(currentPassword)
                            onComplete(false, "Güncel Şifre Yanlış")
                        }
                    }
            } else {
                onComplete(false, "Email Bulunamadı...")
            }
        } else {
            onComplete(false, "Kullanıcının kimliği doğrulanmadı")
        }
    }
}