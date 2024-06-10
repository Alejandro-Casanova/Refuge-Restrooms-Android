package com.example.refugerestrooms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.refugerestrooms.ui.RestroomsViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    restroomsViewModel: RestroomsViewModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Dark Theme",
            style = MaterialTheme.typography.displayMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "",
                style = MaterialTheme.typography.labelSmall
            )
            Switch(
                checked = true,
                onCheckedChange = {

                }
            )

        }
    }
}