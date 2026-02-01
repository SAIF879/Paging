package com.example.paging.domain.util

import com.example.paging.domain.model.User

sealed class UserResult {
    data class Success(val users: List<User>) : UserResult()
    data class Error(val message: String) : UserResult()
}