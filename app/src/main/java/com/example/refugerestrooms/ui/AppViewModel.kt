package com.example.refugerestrooms.ui

import androidx.lifecycle.ViewModel
import com.example.refugerestrooms.data.AppUiDataState
import com.example.refugerestrooms.data.DummyDataSource
import com.example.refugerestrooms.data.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiDataState())
    val uiState: StateFlow<AppUiDataState> = _uiState.asStateFlow()

    private val dummyDataSource = DummyDataSource() // Just for testing

//    private val _currentScreen = MutableStateFlow(AppUiScreenState)
//    val currentScreen: StateFlow<AppUiScreenState> = _currentScreen

    fun setCurrentScreen(screen: Screens) {
        _uiState.update { currentState ->
            currentState.copy(currentScreen = screen)
        }
        //_currentScreen.value = AppUiScreenState(screen)
    }

    init {
        resetApp()
    }

    fun resetApp() {
        _uiState.value = AppUiDataState(restroomsList = dummyDataSource.loadDummyRestrooms())
    }
}