package com.example.refugerestrooms.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String = "",
    buttonIcon: ImageVector = Icons.Filled.Menu,
    onButtonClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        //backgroundColor = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar(title = "TEST NAME", onButtonClicked = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreviewDarkTheme() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar(title = "TEST NAME", onButtonClicked = {})
            }
        }
    }
}