package com.example.paging.data.source

import com.example.paging.data.api.ApiService
import com.example.paging.data.mapper.toDomain
import com.example.paging.domain.repo.UserRepo
import com.example.paging.domain.util.UserResult

class UserRepoImpl(private val apiService: ApiService) : UserRepo {
    override suspend fun getAllUsers(): UserResult {
        return runCatching { apiService.getAllUsers() }.fold(
            onSuccess = { UserResult.Success(it.toDomain()) },
            onFailure = { UserResult.Error(it.message ?: "Something went wrong") }
        )
    }
}