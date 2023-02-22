package com.assignment.windapp.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assignment.windapp.R
import com.assignment.windapp.presentation.common.PrimaryButton
import com.assignment.windapp.presentation.navigation.AppNavigation
import com.assignment.windapp.presentation.ui.appBarTitleStyle
import com.assignment.windapp.presentation.ui.theme.backgroundColor
import com.assignment.windapp.presentation.ui.theme.fontColorAsh
import com.assignment.windapp.presentation.ui.theme.secondaryColor
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(navController: NavHostController) {
    val context = LocalContext.current.applicationContext
    val focusRequester = FocusRequester()
    val bgColor = listOf(
        Color(0XFFFF4848),
        Color(0X69FFBBBB),
        Color(0XFF9A18FF),
    )
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
                    UserNameField(focusRequester = focusRequester)
                    Spacer(modifier = Modifier.height(28.dp))
                    PinField()
                    Spacer(modifier = Modifier.weight(1f, true))
                    PrimaryButton {
                        navController.navigate(AppNavigation.FundTransferScreen.route)
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
fun UserNameField(focusRequester: FocusRequester) {
    var text by remember { mutableStateOf(TextFieldValue("")) }

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
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .background(Color.Transparent)
                    .padding(0.dp)
                    .focusRequester(focusRequester = focusRequester),
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                singleLine = true
            )
        }
    }

}

@Composable
fun PinField() {
    var pinValue by remember {
        mutableStateOf("")
    }
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

        PinTextField(pinText = pinValue, onPinTextChange = { value, otpInputFilled ->
            pinValue = value
        })
    }
}