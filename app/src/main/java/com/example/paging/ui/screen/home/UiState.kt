package com.example.paging.ui.screen.home

import com.example.paging.domain.model.User

sealed class UiState {
    data object Loading : UiState()
    data class Success(val users: List<User>) : UiState()
    data class Error(val message: String) : UiState()
}
