package com.example.paging.domain.repo

import com.example.paging.domain.model.User
import com.example.paging.domain.util.UserResult

interface UserRepo {
    suspend fun getAllUsers(): UserResult
}
