package com.example.paging.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paging.domain.model.User
import com.example.paging.domain.usecase.UserUseCase
import com.example.paging.domain.util.UserResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private var currentPage = 1
    private var isLoading = false
    private val allUsers = mutableListOf<User>()

    init {
        loadInitialUsers()
    }

    private fun loadInitialUsers() {
        _uiState.value = UiState.Loading
        currentPage = 1
        allUsers.clear()
        loadUsers(page = 1, isInitialLoad = true)
    }

    fun loadMoreUsers() {
        val currentState = _uiState.value
        if (isLoading) return
        if (currentState is UiState.Success && !currentState.canLoadMore) return

        currentPage++
        loadUsers(page = currentPage, isInitialLoad = false)
    }

    private fun loadUsers(page: Int, isInitialLoad: Boolean) {
        if (isLoading) return
        isLoading = true

        // Update state to show loading more indicator
        if (!isInitialLoad && _uiState.value is UiState.Success) {
            _uiState.value = (_uiState.value as UiState.Success).copy(isLoadingMore = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = userUseCase(page)
            withContext(Dispatchers.Main.immediate) {
                isLoading = false
                when (result) {
                    is UserResult.Error -> {
                        if (isInitialLoad) {
                            _uiState.value = UiState.Error(result.message)
                        } else {
                            // On pagination error, just stop loading more
                            _uiState.value = UiState.Success(
                                users = allUsers.toList(),
                                isLoadingMore = false,
                                canLoadMore = false,
                                currentPage = currentPage - 1
                            )
                            currentPage-- // Revert page number
                        }
                    }
                    is UserResult.Success -> {
                        val newUsers = result.users
                        allUsers.addAll(newUsers)
                        
                        // Assume no more data if we got less than expected (15 per page)
                        val canLoadMore = newUsers.size >= 15

                        _uiState.value = UiState.Success(
                            users = allUsers.toList(),
                            isLoadingMore = false,
                            canLoadMore = canLoadMore,
                            currentPage = currentPage
                        )
                    }
                }
            }
        }
    }

    fun retry() {
        loadInitialUsers()
    }
}
