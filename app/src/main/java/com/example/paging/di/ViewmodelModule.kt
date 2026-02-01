package com.example.paging.di

import com.example.paging.ui.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel {
        HomeViewModel(userUseCase = get())
    }
}