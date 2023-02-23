package   com.assignment.windapp.presentation.screens.fund_transfer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.assignment.windapp.R
import com.assignment.windapp.presentation.common.PrimaryButton
import com.assignment.windapp.presentation.ui.appBarTitleStyle
import com.assignment.windapp.presentation.ui.theme.secondaryColor
import kotlinx.coroutines.delay

/**
 * Created by Saif on 19/02/2023.
 */

@Composable
fun FundTransferScreen(navHostController: NavHostController) {
    val bgColor = listOf(
        Color(0XFFFF4848),
        Color(0X69FFBBBB),
        Color(0XFF9A18FF),
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Send Fund",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = appBarTitleStyle()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "Back button"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Clear,
                            tint = Color.Transparent,
                            contentDescription = "Dummy action to align title center"
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { paddingValues ->
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
                FundTransferContent()
            }
        }
    )
}

@Composable
fun FundTransferContent() {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UserSection()
        Spacer(modifier = Modifier.height(16.dp))
        AmountSection()
        ErrorSection()
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(onClick = {})
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun UserSection() {
    val gradient = listOf(
        Color(0XFF6E50FF),
        Color(0XFFFF50BA),
    )

    Text(
        text = "Recipient",
        style = TextStyle(
            Color(0XFF75808A),
            fontSize = 16.sp
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_qr),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "@username", style = TextStyle(
                    brush = Brush.horizontalGradient(
                        colors = gradient,
                        tileMode = TileMode.Mirror
                    ),
                    fontSize = 16.sp
                )
            )

            Text(
                text = " - 3CGH...UwvX", style = TextStyle(
                    color = Color(0XFF75808A),
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Composable
fun AmountSection() {
    var textValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    val focusRequester = remember {
        FocusRequester()
    }

    val cardBg = listOf(
        Color(0XFFFCF4FA),
        Color(0X69FDF5F7),
        Color(0XFFFAF5E8),
    )

    val strokeColor = listOf(
        Color(0XFFFFA450),
        Color(0XFF6E50FF)
    )
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        border = BorderStroke(
            width = 2.dp, brush = Brush.linearGradient(
                colors = strokeColor
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = cardBg,
                    tileMode = TileMode.Mirror
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                        .graphicsLayer {
                            translationX = (-16).dp.toPx()
                        }
                        .focusRequester(focusRequester),
                    value = textValue,
                    onValueChange = { newValue ->
                        textValue = newValue
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(
                            text = "0.00", style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    /*leadingIcon = {
                        Text(
                            modifier = Modifier.padding(0.dp),
                            text = "$", style = TextStyle(
                                color = Color.Black,
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }*/
                )
                Text(
                    text = "MAX",
                    modifier = Modifier
                        .weight(0.2F)
                        .align(Alignment.CenterVertically)
                        .background(color = secondaryColor, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp),
                    style = TextStyle(
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "USDC",
                    style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "Balance USDC 381.24",
                    style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Medium)
                )
            }
        }
    }



    LaunchedEffect(Unit) {
        delay(200)
        focusRequester.requestFocus()
    }
}

@Composable
fun ErrorSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Insufficient balance",
            style = TextStyle(
                color = Color(0XFFE04343),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )

        TextButton(
            shape = RoundedCornerShape(size = 8.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = secondaryColor
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(
                text = "Add fund",
                style = TextStyle(color = secondaryColor, fontWeight = FontWeight.Medium)
            )
        }

    }
}