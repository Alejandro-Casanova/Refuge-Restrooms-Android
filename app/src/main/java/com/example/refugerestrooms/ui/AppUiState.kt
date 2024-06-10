package com.example.refugerestrooms.ui

import android.location.Location
import com.example.refugerestrooms.model.Restroom
import com.example.refugerestrooms.ui.screens.Screens

data class AppUiDataState (
    val restroomsList : List<Restroom> = listOf(),
    val currentScreen : Screens = Screens.HomeScreens.Search,
    val currentLocation : Location? = null
)
//data class AppUiScreenState (
//    val currentScreen : Screens = Screens.DrawerScreens.Home
//    //val currentBottomNavScreen : RestroomAppScreensInHomeFromBottomNav = RestroomAppScreensInHomeFromBottomNav.Search,
//)
//data object AppUiScreenState : Screens (
//        route = R.string.search,
//        title = R.string.search,
//        icon = Icons.Filled.Search
//)

/*
 * Data class containing various UI States
 */
data class PreferencesUiState(
    val isThemeSameAsSystem: Boolean = true,
    val isManualDarkThemeOn: Boolean = false,
    val darkThemeOverride: Boolean? = if(isThemeSameAsSystem){
        null
    }else{
        isManualDarkThemeOn
    }
)

