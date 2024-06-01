package com.example.refugerestrooms.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.data.Screens
import com.example.refugerestrooms.data.screensFromDrawer
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (destinationScreen : Screens) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
//        Image(
//            painter = painterResource(R.drawable.logo), //TODO change
//            contentDescription = "App icon"
//        )
        screensFromDrawer.forEach { screen ->
//            Spacer(Modifier.height(24.dp))
//            Text(
//                text = stringResource(screen.title),
//                style = MaterialTheme.typography.labelSmall,
//                modifier = Modifier.clickable {
//                    onDestinationClicked(screen.name)
//                }
//            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDestinationClicked(screen)
                    }
                    .padding(16.dp)

            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(screen.title),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Drawer(onDestinationClicked = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreviewDarkTheme() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Drawer(onDestinationClicked = {})
        }
    }
}