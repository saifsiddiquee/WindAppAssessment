package com.assignment.windapp.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.assignment.windapp.R
import com.assignment.windapp.common.Utils
import com.assignment.windapp.data.remote.dto.AuthModel
import com.assignment.windapp.presentation.common.PrimaryButton
import com.assignment.windapp.presentation.navigation.AppNavigation
import com.assignment.windapp.presentation.ui.appBarTitleStyle
import com.assignment.windapp.presentation.ui.theme.backgroundColor
import com.assignment.windapp.presentation.ui.theme.fontColorAsh
import com.assignment.windapp.presentation.ui.theme.secondaryColor
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current.applicationContext
    val focusRequester = FocusRequester()
    val bgColor = listOf(
        Color(0XFFFF4848),
        Color(0X69FFBBBB),
        Color(0XFF9A18FF),
    )
    val userNameState = remember { mutableStateOf(TextFieldValue()) }

    val pinValue = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Send Fund", style = appBarTitleStyle())
                },
                actions = {
                    TopAppBarActionButton(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_qr),
                        description = "QR"
                    ) {
                        Toast.makeText(context, "Search Click", Toast.LENGTH_SHORT)
                            .show()
                    }

                    TopAppBarActionButton(imageVector = Icons.Outlined.Close, description = "QR") {
                        Toast.makeText(context, "Close Click", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                contentColor = Color.Black,
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
        content = { paddingValues -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(
                        brush = Brush.linearGradient(
                            colors = bgColor
                        ),
                        alpha = 0.05f,
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    UserNameField(focusRequester = focusRequester, userNameState = userNameState)
                    Spacer(modifier = Modifier.height(28.dp))
                    PinField(pin = pinValue)
                    Spacer(modifier = Modifier.weight(1f, true))

                    if (state.error.isNotBlank()) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(CenterHorizontally),
                            text = state.error,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.error
                        )
                    }

                    if (!state.isLoading) {
                        val user = userNameState.value.text.replace("\\s".toRegex(), "").lowercase()
                        PrimaryButton(
                            enable = user.isNotBlank()
                                    && pinValue.value.length == 4
                                    && Utils.userNameValidation(user)
                        ) {
                            viewModel.getUser(
                                AuthModel(
                                    user = user,
                                    pin = pinValue.value
                                )
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.padding(0.dp))
                    }

                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = secondaryColor,
                            modifier = Modifier
                                .align(CenterHorizontally)
                        )
                    }
                }
            }
        },
        backgroundColor = backgroundColor // Set background color to avoid the white flashing when you switch between screens
    )
    LaunchedEffect(Unit) {
        delay(200)
        focusRequester.requestFocus()
    }

    LaunchedEffect(viewModel.loginSuccess.value) {
        if (viewModel.loginSuccess.value) {
            navController.navigate(AppNavigation.FundTransferScreen.route)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clear()
        }
    }
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
fun UserNameField(focusRequester: FocusRequester, userNameState: MutableState<TextFieldValue>) {
    val maxLength = 32
    val space = Regex("\\s")
    Column {
        Text(
            text = stringResource(id = R.string.label_user_field),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            color = fontColorAsh
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "@",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = fontColorAsh
            )
            Spacer(modifier = Modifier.width(2.dp))
            BasicTextField(
                value = userNameState.value,
                onValueChange = {
                    if (it.text.length <= maxLength
                        && !space.containsMatchIn(it.text)
                    ) userNameState.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .background(Color.Transparent)
                    .padding(0.dp)
                    .focusRequester(focusRequester = focusRequester),
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                singleLine = true,
                visualTransformation = CustomTransformation()
            )
        }
    }
}

@Composable
fun PinField(pin: MutableState<String>) {

    Column {
        Text(
            text = stringResource(id = R.string.label_pin_field),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            textAlign = TextAlign.Start,
            color = fontColorAsh
        )

        /* PinInput(
             value = pinValue,
             obscureText = "•"
         ) {
             pinValue = it
         }*/

        PinTextField(pinText = pin.value, onPinTextChange = { value, otpInputFilled ->
            pin.value = value
        })
    }
}

private class CustomTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val newText = text.text.lowercase()
        return TransformedText(
            AnnotatedString(newText),
            OffsetMapping.Identity
        )
    }
}