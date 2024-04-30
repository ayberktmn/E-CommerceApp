package com.ayberk.e_commerceapp.di.module

import com.ayberk.e_commerceapp.data.User.User
import com.ayberk.e_commerceapp.domain.retrofit.Authenticator
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthClass : Authenticator {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun getFirebaseUserUid(): String {
        // Firebase'den kullanıcının UID'sini almak için gerekli işlemleri gerçekleştirin
        return firebaseAuth.currentUser?.uid ?: ""
    }

    override suspend fun signUpWithEmailAndPassword(user: User, password: String) {
        // Kullanıcıyı e-posta ve şifre ile kaydetmek için gerekli işlemleri gerçekleştirin
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        // E-posta ve şifre ile giriş yapmak için gerekli işlemleri gerçekleştirin
        return firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        // Şifre sıfırlama e-postası göndermek için gerekli işlemleri gerçekleştirin
        firebaseAuth.sendPasswordResetEmail(email)
    }

    override suspend fun isCurrentUserExist(): Boolean {
        // Mevcut kullanıcının varlığını kontrol etmek için gerekli işlemleri gerçekleştirin
        return firebaseAuth.currentUser != null
    }

    override suspend fun getCurrentUser(): User {
        // Mevcut kullanıcıyı almak için gerekli işlemleri gerçekleştirin
        val currentUser = firebaseAuth.currentUser
        return User(currentUser?.displayName ?: "", currentUser?.email ?: "")
    }

    override suspend fun getSignedUpUserEmail(): String {
        // Firebase Authentication'tan kullanıcıyı alın
        val currentUser = firebaseAuth.currentUser

        // Kullanıcı varsa ve e-posta adresiyle kayıt olmuşsa, e-posta adresini döndürün
        return if (currentUser != null && currentUser.email != null) {
            currentUser.email!!
        } else {
            // Kullanıcı yoksa veya e-posta adresi kayıtlı değilse boş bir dize döndürün
            ""
        }
    }

    override suspend fun signOut() {
        // Kullanıcıyı çıkış yapmak için gerekli işlemleri gerçekleştirin
        firebaseAuth.signOut()
    }
}