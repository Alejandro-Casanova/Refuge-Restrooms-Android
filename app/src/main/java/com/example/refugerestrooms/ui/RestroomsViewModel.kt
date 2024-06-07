package com.example.refugerestrooms.ui

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.refugerestrooms.RefugeRestroomsApplication
import com.example.refugerestrooms.data.DummyDataSource
import com.example.refugerestrooms.data.LocationTracker
import com.example.refugerestrooms.data.RestroomsRepository
import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.ui.screens.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ApiRequestState {
    data object Success : ApiRequestState
    data object Error : ApiRequestState
    data object Loading : ApiRequestState
}

sealed interface LocationRequestState {
    data object Success : LocationRequestState
    data class Error(val errorMsg: String) : LocationRequestState
    data object Loading : LocationRequestState
}

class RestroomsViewModel(
    private val restroomsRepository: RestroomsRepository,
    private val locationTracker: LocationTracker,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiDataState())
    val uiState: StateFlow<AppUiDataState> = _uiState.asStateFlow()

    var currentLocation by mutableStateOf<Location?>(null)
    var locationRequestState: LocationRequestState by mutableStateOf(LocationRequestState.Loading)
        private set

    fun getLastLocation() {
        viewModelScope.launch {
            locationRequestState = LocationRequestState.Loading
            currentLocation = locationTracker.getLastLocation()
            locationRequestState = if(currentLocation != null) {
                LocationRequestState.Success
            }else{
                LocationRequestState.Error("Could not get location!")
            }
        }
    }

    fun getCurrentLocation() {
        locationRequestState = LocationRequestState.Loading
        locationTracker.getCurrentLocation(
            onGetCurrentLocationFailed = {
                locationRequestState = LocationRequestState.Error(it.message?:"Could not get current location")
            },
            onGetCurrentLocationSuccess = {
                currentLocation = it
                locationRequestState = LocationRequestState.Success
            },
            priority = true
        )
    }

    /** The mutable State that stores the status of the most recent request */
    var apiRequestState: ApiRequestState by mutableStateOf(ApiRequestState.Loading)
        private set

    private val dummyDataSource = DummyDataSource() // Just for testing

//    private val _currentScreen = MutableStateFlow(AppUiScreenState)
//    val currentScreen: StateFlow<AppUiScreenState> = _currentScreen

    fun setCurrentScreen(screen: Screens) {
        _uiState.update { currentState ->
            currentState.copy(currentScreen = screen)
        }
        //_currentScreen.value = AppUiScreenState(screen)
    }

    private fun setRestroomsList(list: List<Restroom>) {
        _uiState.update { currentState ->
            currentState.copy(restroomsList = list)
        }
        //_currentScreen.value = AppUiScreenState(screen)
    }

    init {
        resetApp()
        getRestrooms()
    }

    private fun getRestrooms() {
        viewModelScope.launch {
            apiRequestState = ApiRequestState.Loading
            apiRequestState = try {
                val listResult = restroomsRepository.getRestrooms()
                setRestroomsList(listResult)
                ApiRequestState.Success
            } catch (e: IOException) {
                ApiRequestState.Error
            } catch (e: HttpException) {
                ApiRequestState.Error
            }
        }
    }

    fun resetApp() {
        //_uiState.value = AppUiDataState(restroomsList = dummyDataSource.loadDummyRestrooms())
        _uiState.value = AppUiDataState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RefugeRestroomsApplication)
                val restroomsRepository = application.container.restroomsRepository
                val locationTracker = application.container.locationTracker
                RestroomsViewModel(
                    restroomsRepository = restroomsRepository,
                    locationTracker = locationTracker
                )
            }
        }
    }
}