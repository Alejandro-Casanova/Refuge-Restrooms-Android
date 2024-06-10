package com.example.refugerestrooms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.refugerestrooms.ui.RefugeRestroomsApp
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val restroomsViewModel: RestroomsViewModel = viewModel(factory = RestroomsViewModel.Factory)
            val darkTheme = restroomsViewModel.uiPreferenceState.collectAsState().value.darkThemeOverride
            RefugeRestroomsTheme(darkTheme = darkTheme) {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
                RefugeRestroomsApp(restroomsViewModel)
//                }
            }
        }
    }
}

