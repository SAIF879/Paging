package com.example.paging.data.source

import com.example.paging.data.api.ApiService
import com.example.paging.data.mapper.toDomain
import com.example.paging.domain.repo.UserRepo
import com.example.paging.domain.util.UserResult

class UserRepoImpl(private val apiService: ApiService) : UserRepo {
    override suspend fun getUsers(page: Int): UserResult {
        return runCatching { apiService.getAllUsers(page = page) }.fold(
            onSuccess = { UserResult.Success(it.toDomain()) },
            onFailure = { UserResult.Error(it.message ?: "Something went wrong") }
        )
    }
}