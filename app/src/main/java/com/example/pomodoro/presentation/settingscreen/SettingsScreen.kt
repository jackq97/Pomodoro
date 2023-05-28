package com.example.pomodoro.presentation.settingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pomodoro.R
import com.example.pomodoro.model.local.Settings
import com.example.pomodoro.ui.composables.SettingsSliderText
import com.example.pomodoro.ui.composables.SettingsText
import com.example.pomodoro.ui.composables.SliderComponent
import com.example.pomodoro.ui.theme.AppTheme
import com.example.pomodoro.util.convertMinutesToHoursAndMinutes
import com.example.pomodoro.util.floatToTime
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    var focusSliderPosition by remember { mutableStateOf(0f) }
    var breakSliderPosition by remember { mutableStateOf(0f) }
    var longBreakSliderPosition by remember { mutableStateOf(0f) }
    var noOfRoundsSliderPosition  by remember { mutableStateOf(0f) }

    val settings = viewModel.settings.collectAsState()

    focusSliderPosition = settings.value.focusDur
    breakSliderPosition = settings.value.restDur
    longBreakSliderPosition = settings.value.longRestDur
    noOfRoundsSliderPosition = settings.value.rounds

    AppTheme(darkTheme = false) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {

            Column(modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally) {

                SettingsText(label = stringResource(R.string.focus))

                SettingsSliderText(
                    label = convertMinutesToHoursAndMinutes(
                        floatToTime(
                            focusSliderPosition
                        )
                    )
                )

                SliderComponent(
                    value = focusSliderPosition,
                    onValueChange = {
                        focusSliderPosition = it
                    },
                    minValue = 1f,
                    maxValue = 10f
                )

                SettingsText(label = stringResource(R.string.short_break))
                SettingsSliderText(
                    label = convertMinutesToHoursAndMinutes(
                        floatToTime(
                            breakSliderPosition
                        )
                    )
                )

                SliderComponent(
                    value = breakSliderPosition,
                    onValueChange = {
                        breakSliderPosition = it
                    },
                    minValue = 1f,
                    maxValue = 10f
                )

                SettingsText(label = stringResource(R.string.long_break))
                SettingsSliderText(
                    label = convertMinutesToHoursAndMinutes(
                        floatToTime(
                            longBreakSliderPosition
                        )
                    )
                )

                SliderComponent(
                    value = longBreakSliderPosition,
                    onValueChange = {
                        longBreakSliderPosition = it
                    },
                    minValue = 1f,
                    maxValue = 10f
                )

                SettingsText(label = stringResource(R.string.rounds))
                SettingsSliderText(label = noOfRoundsSliderPosition.toInt().toString())

                SliderComponent(
                    value = noOfRoundsSliderPosition,
                    onValueChange = {
                        noOfRoundsSliderPosition = it
                    },
                    minValue = 1f,
                    maxValue = 10f
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(onClick = {
                        viewModel.saveSettings(
                            Settings(
                                focusDur = focusSliderPosition,
                                restDur = breakSliderPosition,
                                longRestDur = longBreakSliderPosition,
                                rounds = noOfRoundsSliderPosition))
                    }
                    ){

                        Text(text = stringResource(R.string.save_data))
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    TextButton(modifier = Modifier,
                        onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.reset_defaults),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun SettingsPreview(){

    //SettingsScreen(navigator = EmptyDestinationsNavigator)
}