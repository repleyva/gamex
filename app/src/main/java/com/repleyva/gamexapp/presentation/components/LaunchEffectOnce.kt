package com.repleyva.gamexapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun LaunchEffectOnce(block: () -> Unit) {
    var isLaunched by rememberSaveable { mutableStateOf(false) }
    if (!isLaunched) {
        LaunchedEffect(block) {
            block()
            isLaunched = true
        }
    }
}