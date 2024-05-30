package com.example.refugerestrooms.ui

import androidx.lifecycle.ViewModel
import com.example.refugerestrooms.data.AppUiState
import com.example.refugerestrooms.data.DummyDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val dummyDataSource = DummyDataSource() // Just for testing

    init {
        resetApp()
    }

    fun resetApp() {
        _uiState.value = AppUiState(restroomsList = dummyDataSource.loadDummyRestrooms())
    }
}