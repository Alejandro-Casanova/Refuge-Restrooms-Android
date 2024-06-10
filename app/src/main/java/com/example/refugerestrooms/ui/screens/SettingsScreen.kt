package com.example.refugerestrooms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.refugerestrooms.ui.RestroomsViewModel
import com.example.refugerestrooms.ui.theme.RefugeRestroomsTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    restroomsViewModel: RestroomsViewModel,
) {
    val uiPreferencesState = restroomsViewModel.uiPreferenceState.collectAsState().value

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Dark Theme",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        SettingSwitch(
            modifier = Modifier.fillMaxWidth(),
            settingName = "Same as System",
            switchState = uiPreferencesState.isThemeSameAsSystem,
            onSwitchChange = {
                restroomsViewModel.selectSameAsSystemThemePreference(it)
            }
        )
        SettingSwitch(
            modifier = Modifier.fillMaxWidth(),
            settingName = "Set Manually",
            switchState = uiPreferencesState.isManualDarkThemeOn,
            onSwitchChange = {
                restroomsViewModel.selectManualDarkThemePreference(it)
            },
            enabled = !uiPreferencesState.isThemeSameAsSystem
        )
    }
}

@Composable
fun SettingSwitch(
    modifier: Modifier = Modifier,
    settingName: String,
    switchState: Boolean,
    onSwitchChange: ((newState: Boolean) -> Unit)? = null,
    enabled: Boolean = true,
) {
    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(
                    start = 48.dp,
                    end = 48.dp,

                )
                .fillMaxWidth()
        ) {
            Text(
                text = settingName,
                style = MaterialTheme.typography.labelSmall
            )
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchChange,
                enabled = enabled
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingSwitchPreview() {
    RefugeRestroomsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SettingSwitch(
                modifier = Modifier.fillMaxWidth(),
                onSwitchChange = {},
                settingName = "DarkTheme change",
                switchState = true
            )
        }
    }
}