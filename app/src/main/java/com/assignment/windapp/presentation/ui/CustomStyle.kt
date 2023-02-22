package com.assignment.windapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun appBarTitleStyle(): TextStyle {
    return TextStyle(color = Color.Black, fontWeight = FontWeight.W700, fontSize = 24.sp)
}