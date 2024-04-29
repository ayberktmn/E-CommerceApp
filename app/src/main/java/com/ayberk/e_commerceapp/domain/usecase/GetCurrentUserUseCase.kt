package com.ayberk.e_commerceapp.domain.usecase

import com.ayberk.e_commerceapp.common.Resource
import com.ayberk.e_commerceapp.data.User.User
import com.ayberk.e_commerceapp.domain.retrofit.Authenticator
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(): Resource<User> {

        return try {
            Resource.Loading
            Resource.Success(authenticator.getCurrentUser())
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}