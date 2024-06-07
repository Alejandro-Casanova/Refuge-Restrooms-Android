package com.example.refugerestrooms.ui.screens

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.refugerestrooms.R
import com.example.refugerestrooms.ui.LocationRequestState
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class SearchViewActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RefugeRestroomsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchScreen()
                }
            }
        }
    }
}

@Composable
fun EditSearchField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
        },
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onGetCurrentLocationSuccess: () -> Unit = {},
    restroomsViewModel: RestroomsViewModel = viewModel(factory = RestroomsViewModel.Factory),
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val currentLocation = restroomsViewModel.currentLocation
    val locationRequestState = restroomsViewModel.locationRequestState

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
            //            .statusBarsPadding()
                        .padding(horizontal = 40.dp),
            //            .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(0.8f))
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.find_refuge_search_query),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                        .padding(24.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.weight(0.3f))
            EditSearchField( // TODO request query to API
                label = R.string.type_in_your_query,
                leadingIcon = R.drawable.search_icon,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    //.padding(bottom = 32.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "or",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.displayMedium
            )
            //Spacer(modifier = Modifier.weight(0.10f))

            Button(
                onClick = {
                    if(!locationPermissions.allPermissionsGranted) {
                        locationPermissions.launchMultiplePermissionRequest()
                    }
                    restroomsViewModel.getCurrentLocation(
                        onSuccess = {
                            onGetCurrentLocationSuccess()
                            restroomsViewModel.getRestroomsByLocation(it.latitude, it.longitude)
                        }
                    )

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search near your location")
            }
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                when (locationRequestState) {
                    is LocationRequestState.Success -> {
                        Unit
                        //Text(text = "${currentLocation?.latitude ?: 0.0} ${currentLocation?.longitude ?: 0.0}")
                    }

                    is LocationRequestState.Error -> {
                        ErrorScreen(
                            displayText = locationRequestState.errorMsg,
                            displayIcon = false
                        )
                        //Text(text = locationRequestState.errorMsg)
                    }

                    is LocationRequestState.Loading -> {
                        LoadingScreen(displayText = "Fetching Location...")
                        //Text(text = "Loading")
                    }
                }
            }

            //Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreviewDark() {
    RefugeRestroomsTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen()
        }
    }
}