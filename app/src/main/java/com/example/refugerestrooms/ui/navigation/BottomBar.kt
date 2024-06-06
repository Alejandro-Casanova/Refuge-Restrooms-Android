package com.example.refugerestrooms.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.ui.screens.Screens
import com.example.refugerestrooms.ui.screens.screensInHomeFromBottomNav
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onNextScreenClicked: (Screens) -> Unit,
) {
    var currentRoute by rememberSaveable { mutableStateOf(Screens.HomeScreens.Search.route) }

    BottomNavigation(modifier = modifier) {
        screensInHomeFromBottomNav.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "") },
                label = { Text(stringResource(screen.title)) },
                selected = currentRoute == screen.route,
                onClick = {
                    currentRoute = screen.route
                    onNextScreenClicked(screen)
                },
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Spacer(modifier = Modifier.weight(1.0f))
                BottomBar(onNextScreenClicked = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreviewDarkTheme() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Spacer(modifier = Modifier.weight(1.0f))
                BottomBar(onNextScreenClicked = {})
            }
        }
    }
}