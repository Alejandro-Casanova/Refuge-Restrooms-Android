package com.example.refugerestrooms.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.refugerestrooms.ui.LocationRequestState
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    restroomsViewModel: RestroomsViewModel = viewModel(),
) {
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(key1 = locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            restroomsViewModel.getCurrentLocation()
        }
    }

    val currentLocation = restroomsViewModel.currentLocation
    val locationRequestState = restroomsViewModel.locationRequestState

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = locationPermissions.allPermissionsGranted, label = ""
        ) { areGranted ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (areGranted) {
                    when (locationRequestState) {
                        is LocationRequestState.Success -> {
                            Text(text = "${currentLocation?.latitude ?: 0.0} ${currentLocation?.longitude ?: 0.0}")
                        }

                        is LocationRequestState.Error -> {
                            Text(text = locationRequestState.errorMsg)
                        }

                        else -> {
                            Text(text = "Loading")
                        }
                    }
                    Button(
                        onClick = { restroomsViewModel.getCurrentLocation() }
                    ) {
                        Text(text = "Get current location")
                    }
                } else {
                    //locationPermissions.launchMultiplePermissionRequest()
                    Text(text = "We need location permissions for this application.")
                    Button(
                        onClick = { locationPermissions.launchMultiplePermissionRequest() }
                    ) {
                        Text(text = "Accept")
                    }
                }
            }
        }
    }
}