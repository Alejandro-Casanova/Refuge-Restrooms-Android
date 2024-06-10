package com.example.refugerestrooms.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.refugerestrooms.firebase.screens.AuthScreen
import com.example.refugerestrooms.firebase.screens.DatabaseScreen
import com.example.refugerestrooms.firebase.screens.MainViewModel
import com.example.refugerestrooms.ui.screens.Screens
import com.example.refugerestrooms.ui.navigation.BackPressHandler
import com.example.refugerestrooms.ui.navigation.BottomBar
import com.example.refugerestrooms.ui.navigation.Drawer
import com.example.refugerestrooms.ui.navigation.TopBar
import com.example.refugerestrooms.ui.screens.AboutScreen
import com.example.refugerestrooms.ui.screens.MapScreen
import com.example.refugerestrooms.ui.screens.RestroomListScreen
import com.example.refugerestrooms.ui.screens.SearchScreen
import com.example.refugerestrooms.ui.screens.SettingsScreen
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
import kotlinx.coroutines.launch

@Composable
fun RefugeRestroomsApp(
    authViewModel: MainViewModel = viewModel(),
    restroomsViewModel: RestroomsViewModel,
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val uiState by restroomsViewModel.uiState.collectAsState()
    val currentScreen = uiState.currentScreen
    val darkTheme = restroomsViewModel.uiPreferenceState.collectAsState().value.darkThemeOverride

    if (scaffoldState.drawerState.isOpen) {
        BackPressHandler (onBackPressed = {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        })
    }
//    else {
//        BackPressHandler {
//            // navController.navigateUp()
//            navController.popBackStack()
//        }
//    }

    var topBar : @Composable () -> Unit = {
        TopBar(
            title = stringResource(currentScreen.title),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        )
    }

    if (currentScreen != Screens.DrawerScreens.Home && currentScreen !is Screens.HomeScreens) {
        topBar = {
            TopBar(
                title = stringResource(currentScreen.title),
                buttonIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    }

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen == Screens.DrawerScreens.Home || currentScreen is Screens.HomeScreens) {
            BottomBar(
                onNextScreenClicked = { screen ->
                    //navController.navigate(screen.route)
                    //appViewModel.setCurrentScreen(it)
                    if(screen == Screens.HomeScreens.Search){
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    }else{
                        val ret = navController.popBackStack(screen.route, inclusive = false)
                        Log.d("NAVIGATION_DEBUG_2", ret.toString())
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }

    Scaffold(
        modifier = Modifier,
            //.statusBarsPadding(),
            //.safeDrawingPadding(),
        topBar = {
            topBar()
        },
        bottomBar = {
            RefugeRestroomsTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    bottomBar()
                }
            }
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            RefugeRestroomsTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Drawer(onDestinationClicked = { screen ->
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                        val ret = navController.popBackStack(
                            Screens.DrawerScreens.Home.route,
                            inclusive = false
                        )
                        Log.d("NAVIGATION_DEBUG_2", ret.toString())
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                        //appViewModel.setCurrentScreen(screen)
                    })
                }
            }
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
    ) { innerPadding ->
        RefugeRestroomsTheme(darkTheme = darkTheme) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationHost(
                    navController = navController,
                    viewModel = restroomsViewModel,
                    scaffoldInnerPadding = innerPadding,
                    authViewModel = authViewModel,
                )
            }
        }
//        NavigationHost(
//            navController = navController,
//            viewModel = restroomsViewModel,
//            scaffoldInnerPadding = innerPadding
//       )
    }

}

@Composable
fun NavigationHost(
    navController: NavController,
    viewModel: RestroomsViewModel,
    authViewModel: MainViewModel,
    //modifier : Modifier = Modifier,
    scaffoldInnerPadding : PaddingValues = PaddingValues(0.dp)
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.DrawerScreens.Home.route,
    ) {
        composable(Screens.DrawerScreens.Home.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.Home)
//            LocationScreen(
//                restroomsViewModel = viewModel,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(scaffoldInnerPadding))
            Text(text="HOME TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
        }
        // SETTINGS SCREEN
        composable(Screens.DrawerScreens.FireBaseAuth.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.FireBaseAuth)
            //Text(text="FIREBASE AUTH TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
            AuthScreen(
                modifier = Modifier.fillMaxSize(),
                authViewModel = authViewModel,
            )
        }
        composable(Screens.DrawerScreens.FireBaseStore.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.FireBaseStore)
            //Text(text="FIREBASE STORE TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
            DatabaseScreen(
                modifier = Modifier.fillMaxSize(),
                restroomsViewModel = viewModel,
                authViewModel = authViewModel,
            )
        }
        composable(Screens.DrawerScreens.Settings.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.Settings)
            SettingsScreen(
                restroomsViewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldInnerPadding)
            )
            //Text(text="SETTINGS TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
        }
        // ABOUT SCREEN
        composable(Screens.DrawerScreens.About.route) {
            viewModel.setCurrentScreen(Screens.DrawerScreens.About)
            //Text(text="ABOUT TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
            AboutScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldInnerPadding),
            )
        }
        // SEARCH SCREEN
        composable(Screens.HomeScreens.Search.route) {
            viewModel.setCurrentScreen(Screens.HomeScreens.Search)
            //Text(text="SEARCH TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
            SearchScreen(
                restroomsViewModel = viewModel,
                onGetCurrentLocationSuccess = {
                    navController.navigate(Screens.HomeScreens.List.route) {
                        launchSingleTop = true
                    }
                },
                onQuerySearchButtonPressed = {
                    navController.navigate(Screens.HomeScreens.List.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldInnerPadding)
            )
        }
        // RESTROOM LIST SCREEN
        composable(Screens.HomeScreens.List.route) {
            viewModel.setCurrentScreen(Screens.HomeScreens.List)
            //Text(text="LIST TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
            RestroomListScreen(
                modifier = Modifier.fillMaxSize(),
                restroomsViewModel = viewModel,
                lazyListContentPadding = scaffoldInnerPadding
            )
        }
        // MAP SCREEN
        composable(Screens.HomeScreens.Map.route) {
            viewModel.setCurrentScreen(Screens.HomeScreens.Map)
            MapScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldInnerPadding),
                restroomsViewModel = viewModel
            )
            //Text(text="MAP TEST", style = MaterialTheme.typography.displayMedium, modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RefugeRestroomsApp(viewModel(), viewModel())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreviewDarkTheme() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RefugeRestroomsApp(viewModel(), viewModel())
        }
    }
}