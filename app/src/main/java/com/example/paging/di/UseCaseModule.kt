package com.example.paging.di

import com.example.paging.domain.usecase.UserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { UserUseCase(get()) }
}