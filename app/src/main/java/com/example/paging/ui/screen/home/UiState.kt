package com.example.paging.ui.screen.home

import com.example.paging.domain.model.User

sealed class UiState {
    data object Loading : UiState()
    data class Success(
        val users: List<User>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true,
        val currentPage: Int = 1
    ) : UiState()
    data class Error(val message: String) : UiState()
}
