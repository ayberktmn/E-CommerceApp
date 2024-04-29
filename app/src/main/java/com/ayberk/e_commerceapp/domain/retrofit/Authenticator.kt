package com.ayberk.e_commerceapp.domain.retrofit

import com.ayberk.e_commerceapp.data.User.User
import com.google.firebase.auth.AuthResult

interface Authenticator {

    suspend fun getFirebaseUserUid(): String

    suspend fun signUpWithEmailAndPassword(user: User, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun sendPasswordResetEmail(email: String)

    suspend fun isCurrentUserExist(): Boolean

    suspend fun getCurrentUser(): User

    suspend fun signOut()

}