package dev.miyatech.buzzbee.login


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.alerts.ShowLoading
import dev.miyatech.buzzbee.ui.alerts.ShowToast
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui_components.PasswordField
import dev.miyatech.buzzbee.ui_components.TextFieldWithImage
import dev.miyatech.buzzbee.viewmodel.ViewModelLogin


@Composable
fun LoginScreen(navController: NavHostController) {

    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    var viewmodel1 = ViewModelLogin()
    try {
        var act = context as MainActivity
        viewmodel1 = ViewModelProvider(act).get(ViewModelLogin::class.java)
    } catch (e: Exception) {
    }
    val viewmodel by remember { mutableStateOf(viewmodel1) }

    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }





    LaunchedEffect(key1 = null) {

        errorMsg = ""
        try {
            var act = context as MainActivity
            viewmodel.login.observe(act) { response ->

                when (response) {

                    is NetworkResult.Loading -> {
                        isLoading = true

                    }

                    is NetworkResult.Success -> {

                        isLoading = false

                        if (response.data.errorCode == 0) {


                        } else {
                            showToast = true
                            errorMsg = response.data.message
                        }
                    }

                    is NetworkResult.Error -> {
                        showToast = true
                        isLoading = false

                        errorMsg = response.message

                    }

                    else -> {
                        isLoading = false
                    }
                }

                println("my response one " + response)

            }
        } catch (e: Exception) {
        }
    }


    Surface {

        val backgroundImagePainter: Painter =
            painterResource(id = dev.miyatech.buzzbee.R.drawable.login_vector)

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(appThemePrimary)

            ) {}
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()

                    .background(Color(0xFFF1F1F1))
                    .paint(
                        backgroundImagePainter,
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.TopStart,
                    )
            ) {


                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(appThemePrimary)
                ) {
                    Text(
                        text = "Hello",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(Modifier.height(5.dp)) {

                    }
                    Text(text = "Welcome back!", color = Color.White, fontSize = 18.sp)

                }


            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(all = 15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Color(0xFFFFFFFF)
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(all = 15.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Login",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.clickable {
                            mobileNumber = "6381095545"
                            password = "1234"

                        })


                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Lorem ipsum dolor sit amet consectetur.Non elementum" + "sedmorbi mauris ultrices.",

                        textAlign = TextAlign.Center, fontSize = 12.sp,


                        fontStyle = FontStyle.Normal, modifier = Modifier.clickable {

                        })
                    Spacer(modifier = Modifier.height(15.dp))

                    TextFieldWithImage(
                        value = mobileNumber,
                        onChange = { data ->
                            if (viewmodel.isValidIndianMobileNumber(data)) {
                                if (data.length <= 13) {
                                    viewmodel.checkingMobile(data)
                                    mobileNumber = data
                                } else {
                                    viewmodel.checkingMobile(data)
                                    mobileNumber = data.substring(0, 13)
                                }
                            } else {
                                viewmodel.checkingMobile("")
                                mobileNumber = ""
                            }


                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = "Mobile",
                        placeholder = "Enter your mobile number",
                        icon = Icons.Default.Person,
                        keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                        )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (viewmodel.isMobileError) {
                        Text(
                            " Please enter valid mobile number ",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            fontSize = 11.sp

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    PasswordField(
                        value = password,
                        onChange = { data ->

                            if (data.length <= 18) {
                                viewmodel.checkingPassword(data)
                                password = data
                            } else {
                                viewmodel.checkingPassword(data)
                                password = data.substring(0, 18)
                            }
                        },
                        submit = {

                            viewmodel.submit(mobileNumber, password, context, navController)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = "Password",
                        placeholder = "Enter your Password",
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    if (viewmodel.isPasswordError) {
                        Text(
                            " Please enter valid password ",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        onClick = {
                            println("name")

                            viewmodel.submit(mobileNumber, password, context, navController)

                        },
                        enabled = viewmodel.submitLoginValidation(mobileNumber, password),

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text("Login")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.Register.route)
                            }, horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text("Forget Password?", fontSize = 13.sp)
                        Row {
                            Text("New User?", fontSize = 13.sp)
                            Text(
                                " Register Here!", fontSize = 13.sp,
                                color = appThemeAccident,
                            )
                        }
                    }

                }
            }
        }
        ShowLoading(isLoading = isLoading, submit = {

        })

        if (showToast) {
            ShowToast(
                outSide = {
                },
                onDismiss = {
                    errorMsg = ""
                    showToast = false
                },
                onDismissCloseIcons = {
                    errorMsg = ""
                    showToast = false


                },
                title = "", subTitle = "" + errorMsg,
            )
        }


    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    LoginScreen(rememberNavController())
}