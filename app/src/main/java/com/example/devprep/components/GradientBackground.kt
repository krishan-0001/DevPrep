package com.example.devprep.components

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.appBackground(): Modifier = this.background(
    Brush.verticalGradient(
        colors = listOf(
            Color(0xFF5B3FD3),
            Color(0xFF8E7CFF)
        )
    )
)
