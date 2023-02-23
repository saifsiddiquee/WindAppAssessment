package   com.assignment.windapp.presentation.screens.fund_transfer

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.assignment.windapp.R
import com.assignment.windapp.data.remote.dto.AccountInfo
import com.assignment.windapp.domain.model.User
import com.assignment.windapp.presentation.common.PrimaryButton
import com.assignment.windapp.presentation.screens.auth.UserViewModel
import com.assignment.windapp.presentation.ui.appBarTitleStyle
import com.assignment.windapp.presentation.ui.theme.secondaryColor
import kotlinx.coroutines.delay

/**
 * Created by Saif on 19/02/2023.
 */

@Composable
fun FundTransferScreen(
    navHostController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val bgColor = listOf(
        Color(0XFFFF4848),
        Color(0X69FFBBBB),
        Color(0XFF9A18FF),
    )
    val userData = viewModel.getUser()

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
                FundTransferContent(userData = userData)
            }
        }
    )
}

@Composable
fun FundTransferContent(userData: User) {
    val textValue = remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val notValidAmount = textValue.value.text.isNotEmpty()
                && textValue.value.text.trim().toDouble() > userData.data.accountInfo.balance
        UserSection(userData)
        Spacer(modifier = Modifier.height(16.dp))
        AmountSection(accountInfo = userData.data.accountInfo, textValue = textValue)
        if (notValidAmount
        ) {
            ErrorSection()
        } else {
            Spacer(modifier = Modifier.padding(0.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(onClick = {}, enable = !notValidAmount && textValue.value.text.isNotEmpty())
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun UserSection(userData: User) {
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
                text = "@${userData.data.userInfo.userName}",
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        colors = gradient,
                        tileMode = TileMode.Mirror
                    ),
                    fontSize = 16.sp,
                ),
            )

            Text(
                text = " - ${userData.data.userInfo.walletAddress}",
                style = TextStyle(
                    color = Color(0XFF75808A),
                    fontSize = 16.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun AmountSection(accountInfo: AccountInfo, textValue: MutableState<TextFieldValue>) {

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
                    value = textValue.value,
                    onValueChange = {
                        textValue.value = it
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
                TextButton(
                    modifier = Modifier
                        .weight(0.2F)
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(size = 8.dp),
                    onClick = { textValue.value = TextFieldValue(accountInfo.balance.toString()) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (textValue.value.text.isNotEmpty() && textValue.value.text
                                .trim()
                                .toDouble() == accountInfo.balance
                        ) secondaryColor else Color.White,
                        contentColor = secondaryColor
                    ),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "MAX",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = if (textValue.value.text.isNotEmpty() && textValue.value.text
                                    .trim()
                                    .toDouble() == accountInfo.balance
                            ) Color.White else secondaryColor,
                        )
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = accountInfo.currency,
                    style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "Balance ${accountInfo.currency} ${accountInfo.balance}",
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