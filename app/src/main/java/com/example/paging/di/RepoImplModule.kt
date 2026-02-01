package com.example.paging.di

import com.example.paging.data.source.UserRepoImpl
import com.example.paging.domain.repo.UserRepo
import org.koin.dsl.module

val repoImplModule = module {

    single<UserRepo> {
        UserRepoImpl(apiService = get())
    }
}