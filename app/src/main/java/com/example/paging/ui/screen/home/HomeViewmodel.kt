package com.example.paging.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = userUseCase()
            withContext(Dispatchers.Main.immediate) {
                when (result) {
                    is UserResult.Error -> {
                        _uiState.value = UiState.Error(result.message)
                    }
                    is UserResult.Success -> {
                        _uiState.value = UiState.Success(result.users)
                    }
                }
            }
        }
    }

}
