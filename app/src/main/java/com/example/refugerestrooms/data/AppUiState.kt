package com.example.refugerestrooms.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import com.example.refugerestrooms.R
import com.example.refugerestrooms.model.Restroom

data class AppUiDataState (
    val restroomsList : List<Restroom> = listOf(),
    val currentScreen : Screens = Screens.HomeScreens.Search
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
