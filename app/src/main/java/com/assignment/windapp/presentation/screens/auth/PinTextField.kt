package com.assignment.windapp.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.assignment.windapp.presentation.ui.theme.borderColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PinTextField(
    modifier: Modifier = Modifier,
    pinText: String,
    pinCount: Int = 4,
    onPinTextChange: (String, Boolean) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        value = pinText,
        modifier = modifier.onFocusChanged {
            isFocused = it.isFocused
        },
        cursorBrush = SolidColor(Color.Black),
        onValueChange = {
            if (it.length <= pinCount) {
                onPinTextChange.invoke(it, it.length == pinCount)
            }
        },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(pinCount) { index ->
                    CharView(
                        index = index,
                        text = pinText,
                        isCursorVisible = if (isFocused) pinText.length == index else false,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    isCursorVisible: Boolean = false,
    obscureText: String = "•"
) {
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }

    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Text(
        modifier = Modifier
            .width(72.dp)
            .padding(2.dp)
            .bottomBorder(
                4.dp, when {
                    char.isNotEmpty() -> Color.Black
                    else -> borderColor
                }
            ),
        text = if (isCursorVisible) cursorSymbol else if (obscureText.isNotBlank() && char
                .isBlank().not()
        ) obscureText else char,
        style = MaterialTheme.typography.h4,
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)