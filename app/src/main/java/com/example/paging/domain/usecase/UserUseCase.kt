package com.example.paging.domain.usecase

import com.example.paging.domain.repo.UserRepo
import com.example.paging.domain.util.UserResult

class UserUseCase(private val userRepo: UserRepo) {
    suspend operator fun invoke(page: Int): UserResult {
        return userRepo.getUsers(page)
    }
}