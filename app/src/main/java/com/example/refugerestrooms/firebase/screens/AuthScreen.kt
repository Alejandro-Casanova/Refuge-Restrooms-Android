package com.example.refugerestrooms.firebase.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme
import com.firebase.ui.auth.AuthUI

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    authViewModel: MainViewModel = viewModel(),
) {

    var showSignIn by remember {mutableStateOf(false)}

    val signInStatus by authViewModel.signInStatus.collectAsStateWithLifecycle()
    val signedInUser by authViewModel.signedInUser.collectAsStateWithLifecycle()

    val context = LocalContext.current

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
            showSignIn = true
        }) {
            Text("Sign In")
        }

        Spacer(Modifier.padding(2.dp))
        Button(onClick = {
            AuthUI.getInstance().signOut(context)
            authViewModel.onSignOut()
        }) {
            Text("Sign Out")
        }
    }
    if (showSignIn) {
        SignInScreen { result ->
            // (4) Handle the sign-in result callback
            if (result.resultCode == Activity.RESULT_OK) {
                authViewModel.onSignedIn()
            } else {
                val response = result.idpResponse
                if (response == null) {
                    authViewModel.onSignInCancel()
                } else {
                    val errorCode = response.error?.errorCode
                    authViewModel.onSignInError(errorCode)
                }
            }

            showSignIn = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    RefugeRestroomsTheme {
        AuthScreen()
    }
}