package com.example.refugerestrooms.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.refugerestrooms.R

//enum class RestroomAppScreensFromDrawer(
//    @StringRes val title: Int,
//    val icon: ImageVector,
//) {
//    Home(
//        title = R.string.home,
//        icon = Icons.Filled.Home
//    ),
//    About(
//        title = R.string.about,
//        icon = Icons.Filled.Info
//    ),
//}
//
//enum class RestroomAppScreensInHomeFromBottomNav(
//    @StringRes val title: Int,
//    val icon: ImageVector,
//) {
//    Search(
//        title = R.string.search,
//        icon = Icons.Filled.Search
//    ),
//    List(
//        title = R.string.list_view,
//        icon = Icons.Filled.ViewList
//    ),
//    Map(
//        title = R.string.map_view,
//        icon = Icons.Filled.Map
//    )
//}

sealed class Screens(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector,
) {

    sealed class HomeScreens(
        route: String,
        title: Int,
        icon: ImageVector
    ) : Screens(route, title, icon) {
        data object Search : HomeScreens (
            route = "SEARCH",
            title = R.string.search,
            icon = Icons.Filled.Search
        )
        data object List : HomeScreens (
            route = "LIST",
            title = R.string.list_view,
            icon = Icons.Filled.ViewList
        )
        data object Map : HomeScreens (
            route = "MAP",
            title = R.string.map_view,
            icon = Icons.Filled.Map
        )
    }

    sealed class DrawerScreens(
        route: String,
        title: Int,
        icon: ImageVector
    ) : Screens(route, title, icon) {
        data object Home : DrawerScreens (
            route = "HOME",
            title = R.string.home,
            icon = Icons.Filled.Home
        )
        data object Settings : DrawerScreens (
            route = "SETTINGS",
            title = R.string.settings,
            icon = Icons.Filled.Settings
        )
        data object About : DrawerScreens (
            route = "ABOUT",
            title = R.string.about,
            icon = Icons.Filled.Info
        )
    }
}

val screensInHomeFromBottomNav = listOf(
    Screens.HomeScreens.Search,
    Screens.HomeScreens.List,
    Screens.HomeScreens.Map
)

val screensFromDrawer = listOf(
    Screens.DrawerScreens.Home,
    Screens.DrawerScreens.Settings,
    Screens.DrawerScreens.About
)