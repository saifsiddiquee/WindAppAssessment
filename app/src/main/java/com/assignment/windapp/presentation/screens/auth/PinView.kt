package com.assignment.windapp.presentation.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.assignment.windapp.presentation.ui.theme.secondaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TextCell(
    modifier: Modifier,
    value: Char?,
    isCursorVisible: Boolean = false,
    obscureText: String?
) {
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

    Box(
        modifier = modifier
    ) {
        Text(
            text = if (isCursorVisible) cursorSymbol else if (!obscureText.isNullOrBlank() && value?.toString()
                    .isNullOrBlank().not()
            ) obscureText else value?.toString() ?: "",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h4,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
/**
 * @param obscureText Set null, if want to show the number.
 */
@Composable
fun PinInput(
    modifier: Modifier = Modifier,
    length: Int = 4,
    value: String = "",
    disableKeypad: Boolean = false,
    obscureText: String? = "*",
    onValueChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    TextField(
        readOnly = disableKeypad,
        value = value,
        onValueChange = {
            if (it.length <= length) {
                if (it.all { c -> c in '0'..'9' }) {
                    onValueChanged(it)
                }
                if (it.length >= length) {
                    keyboard?.hide()
                }
            }
        },
        // Hide the text field
        modifier = Modifier
            .size(0.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        visualTransformation = PasswordVisualTransformation()
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(length) {
            TextCell(
                modifier = Modifier
                    .width(72.dp)
                    .height(56.dp)
                    .padding(2.dp)
                    .bottomBorder(
                        4.dp, when {
                            value.isNotEmpty() -> secondaryColor
                            else -> {
                                Color.LightGray
                            }
                        }
                    )
                    .clickable {
                        focusRequester.requestFocus()
                        keyboard?.show()
                    },
                value = value.getOrNull(it),
                isCursorVisible = value.length == it,
                obscureText
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}