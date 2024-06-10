package com.example.refugerestrooms.firebase.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

@Composable
fun DatabaseScreen(
    modifier: Modifier = Modifier,
    authViewModel: MainViewModel = viewModel(),
    restroomsViewModel: RestroomsViewModel = viewModel(factory = RestroomsViewModel.Factory),
) {
    val currentLocation = restroomsViewModel.uiState.collectAsState().value.currentLocation

    val signInStatus by authViewModel.signInStatus.collectAsStateWithLifecycle()
    val signedInUser by authViewModel.signedInUser.collectAsStateWithLifecycle()
    val userDataFromDB by authViewModel.userDataFromDB.collectAsStateWithLifecycle()
    val userDataFlowFromDB by authViewModel.userDataFlowFromDB.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,//Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.padding(2.dp))
        Text("Sign-in Status: $signInStatus")

        Spacer(Modifier.padding(2.dp))
        Text("User Id: ${signedInUser?.uid}")

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            if(currentLocation != null){
                authViewModel.writeToDatabase(currentLocation.latitude, currentLocation.longitude)
            }
        }) {
            Text("Write Data")
        }

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            authViewModel.readFromDatabase()
        }) {
            Text("Read Data")
        }

        Spacer(Modifier.padding(12.dp))
        Text("User Data (One-time Read)")
        Text("----------------------------------------------")
        Text("User Name: ${userDataFromDB?.userName}")
        Text("Latitude: ${userDataFromDB?.latitude}")
        Text("Longitude: ${userDataFromDB?.longitude}")

        Spacer(Modifier.padding(12.dp))
        Text("User Data (Continuous Read - Flow)")
        Text("----------------------------------------------")
        Text("User Name: ${userDataFlowFromDB?.userName}")
        Text("Latitude: ${userDataFlowFromDB?.latitude}")
        Text("Longitude: ${userDataFlowFromDB?.longitude}")
    }
}



@Preview(showBackground = true)
@Composable
fun DatabaseScreenPreview() {
    RefugeRestroomsTheme {
        DatabaseScreen()
    }
}