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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationCity
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.model.StateList
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.alerts.ShowLoading
import dev.miyatech.buzzbee.ui.alerts.ShowOTPAlerts
import dev.miyatech.buzzbee.ui.alerts.ShowToast
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui_components.BottomSheetStateList
import dev.miyatech.buzzbee.ui_components.PasswordField
import dev.miyatech.buzzbee.ui_components.TextFieldWithImage
import dev.miyatech.buzzbee.ui_components.TextFieldWithImageSelect
import dev.miyatech.buzzbee.viewmodel.ViewModelLogin


data class RegisterRequestModel(
    var name: String = "",
    var states: String = "",
    var district: String = "",
    var city: String = "",
    var mobile: String = "",
    var password: String = "",
)


@Composable
fun RegisterScreen(navController: NavHostController) {

    var dataInputs by remember { mutableStateOf(RegisterRequestModel()) }
    val context = LocalContext.current
    var viewmodel1 = ViewModelLogin()
    try {
        val act = context as MainActivity
        viewmodel1 = ViewModelProvider(act)[ViewModelLogin::class.java]
    } catch (e: Exception) {
        println("errors " + e)
    }

    var showAlert by remember { mutableStateOf(false) }
    var showSuccessAlerts by remember { mutableStateOf(false) }
    var showOtp by remember { mutableStateOf(false) }
    val viewmodel by remember { mutableStateOf(viewmodel1) }

    var isStateError by remember {
        mutableStateOf(false)
    }
    var isDistrictError by remember {
        mutableStateOf(false)
    }
    var isCityError by remember {
        mutableStateOf(false)
    }

    var stateList by remember {
        mutableStateOf(viewmodel.stateListData)
    }

    var districtList by remember {
        mutableStateOf(ArrayList<StateList>())
    }
    var cityList by remember {
        mutableStateOf(ArrayList<StateList>())
    }


    var dig_title by remember {
        mutableStateOf("")
    }

    var dig_array by remember {
        mutableStateOf(ArrayList<StateList>())
    }

    var stateId = ""
    var districtId = ""
    var cityId = ""
    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }


    LaunchedEffect(key1 = null) {
        viewmodel.stateList.observe(context as MainActivity) { response ->

            when (response) {
                is NetworkResult.Loading -> {
                    isLoading = true
                }

                is NetworkResult.Success -> {
                    isLoading = false
                    if (response.data.errorCode == 0) {

                        dig_array = stateList
                        println("State list " + stateList.size + " dig_array " + dig_array.size)

                        showAlert = true

                        dig_title = "State"
                        isStateError = false
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

        }
        viewmodel.districtList.observe(context) { response ->
            districtList = arrayListOf()
            when (response) {
                is NetworkResult.Loading -> {
                    isLoading = true
                }

                is NetworkResult.Success -> {
                    isLoading = false
                    if (response.data.errorCode == 0) {
                        districtList.addAll(response.data.results)
                        showAlert = true
                        dig_array = districtList
                        dig_title = "District"
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

        }
        viewmodel.cityList.observe(context) { response ->
            cityList = arrayListOf()
            when (response) {
                is NetworkResult.Loading -> {
                    isLoading = true
                }

                is NetworkResult.Success -> {
                    isLoading = false
                    if (response.data.errorCode == 0) {
                        cityList.addAll(response.data.results)
                        showAlert = true
                        dig_array = cityList
                        dig_title = "City"
                        isCityError = false
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

        }
        viewmodel.register.observe(context) { response ->
            cityList = arrayListOf()
            when (response) {
                is NetworkResult.Loading -> {
                    isLoading = true
                }

                is NetworkResult.Success -> {
                    isLoading = false

                    println("test " + response.data.message)
                    if (response.data.errorCode == 0) {
                        showOtp = true
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

        }
    }



    Surface {

        val backgroundImagePainter: Painter = painterResource(id = R.drawable.login_vector)
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

            ) {

            }
            Column(

                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start,

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
//                    Text(
//                        text = "Hello",
//                        color = Color.White,
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Box(Modifier.height(5.dp)) {
//
//                    }
//                    Text(text = "Welcome back!", color = Color.White, fontSize = 18.sp)

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
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(text = "Register",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable {
                                dataInputs =
                                    dataInputs.copy(mobile = "9876543212", password = "Sandy@123")

                            })

                    Spacer(modifier = Modifier.height(8.dp))

                    TextFieldWithImage(
                        value = dataInputs.name,
                        onChange = { data ->
                            dataInputs = dataInputs.copy(name = data)
                            if (data.length <= 25) {
                                dataInputs = dataInputs.copy(name = data)
                            } else {
                                dataInputs = dataInputs.copy(name = data.substring(0, 25))
                            }


                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = "Name",
                        placeholder = "Enter your name",
                        icon = Icons.Default.Person,
                        keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),

                        )



                    Spacer(modifier = Modifier.height(15.dp))
                    TextFieldWithImageSelect(
                        value = dataInputs.states,
                        onChange = { data ->
                            dataInputs = dataInputs.copy(states = data)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                viewmodel.getStateList(context)
                            },
                        label = "State",
                        placeholder = "Select state",
                        icon = Icons.Default.Language

                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (isStateError) {
                        Text(
                            " Select state ",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            fontSize = 11.sp

                        )
                    } else {

                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    TextFieldWithImageSelect(
                        value = dataInputs.district,
                        onChange = { data ->
                            dataInputs = dataInputs.copy(district = data)
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                isDistrictError = false
                                if (viewmodel.stateValidaction(dataInputs.states) == true) {
                                    isStateError = true

                                } else {

                                    viewmodel.districtList(stateId, context)

                                }
                            },
                        label = "District",
                        placeholder = "Select district",
                        icon = Icons.Default.Business,


                        )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (isDistrictError) {
                        Text(
                            " Select district ",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            fontSize = 11.sp

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    TextFieldWithImageSelect(
                        value = dataInputs.city,
                        onChange = { data ->


                            dataInputs = dataInputs.copy(city = data)

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (viewmodel.stateValidaction(dataInputs.district) == true) {
                                    isDistrictError = true
                                } else {
                                    viewmodel.cityList(districtId, context)
                                }

                            },
                        label = "City",
                        placeholder = "Select City",
                        icon = Icons.Default.LocationCity,

                        )
                    Spacer(modifier = Modifier.height(5.dp))
                    if (isCityError) {
                        Text(
                            " Select city ",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            fontSize = 11.sp

                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TextFieldWithImage(
                        value = dataInputs.mobile,
                        onChange = { data ->


                            if (viewmodel.isValidIndianMobileNumber(data)) {
                                if (data.length <= 13) {
                                    viewmodel.checkingMobile(data)
                                    dataInputs = dataInputs.copy(mobile = data)

                                } else {
                                    viewmodel.checkingMobile(data)
                                    dataInputs = dataInputs.copy(mobile = data.substring(0, 13))
                                }
                            } else {
                                viewmodel.checkingMobile("")
                                dataInputs.mobile = ""
                            }


                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = "Mobile",
                        placeholder = "Enter your mobile number",
                        icon = Icons.Default.Call,
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
                        value = dataInputs.password,
                        onChange = { data ->
                            dataInputs = dataInputs.copy(password = data)
                        },
                        submit = {

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


                    if (showAlert) {
                        BottomSheetStateList(dig_title,
                            list = dig_array,
                            onDismiss = { showAlert = false },
                            onItemSelected = { temp ->
                                showAlert = false


                                if (dig_title.equals("State")) {
                                    dataInputs = dataInputs.copy(states = temp.name)
                                    dataInputs = dataInputs.copy(district = "")
                                    dataInputs = dataInputs.copy(city = "")
                                    stateId = temp.id
                                    districtId = ""
                                    cityId = ""

                                } else if (dig_title.equals("District")) {
                                    districtId = temp.id
                                    dataInputs = dataInputs.copy(district = temp.name)
                                    dataInputs = dataInputs.copy(city = "")
                                    cityId = ""
                                } else {
                                    dataInputs = dataInputs.copy(city = temp.name)
                                    cityId = "" + temp.id
                                }
                            })
                    }

                    if (showSuccessAlerts) {
                        ShowToast(
                            outSide = {

                            },
                            onDismiss = {
                                showSuccessAlerts = false
                                navController.popBackStack()
                            },
                            onDismissCloseIcons = {
                                showSuccessAlerts = false
                                navController.popBackStack()
                            },
                            title = "", subTitle = "Successfully Register",
                        )
                    }

                    ShowLoading(isLoading = isLoading, submit = {
                        isLoading = false
                    })

                    if (showToast) {
                        ShowToast(
                            outSide = {

                            },
                            onDismiss = {
                                showToast = false

                            },
                            onDismissCloseIcons = {
                                showToast = false
                            },
                            title = "", subTitle = errorMsg,
                        )
                    }
                    if (showOtp) {
                        ShowOTPAlerts(length = 4, onFilled = {}, submit = {
                            showOtp = false
                            showSuccessAlerts = true
                        }, close = {
                            showOtp = false
                        }, otp_code = "1234"
                        )
                    }
                    Button(
                        onClick = {
//                            showSuccessAlerts = true


                            viewmodel.registerApi(
                                dataInputs.name,
                                stateId,
                                districtId,
                                cityId,
                                dataInputs.mobile,
                                dataInputs.password, "", context
                            )
                        },
                        enabled = viewmodel.submitValidation(
                            dataInputs.name,
                            dataInputs.states,
                            dataInputs.district,
                            dataInputs.city,
                            dataInputs.mobile,
                            dataInputs.password
                        ),

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            "Save ", color = if (viewmodel.submitValidation(
                                    dataInputs.name,
                                    dataInputs.states,
                                    dataInputs.district,
                                    dataInputs.city,
                                    dataInputs.mobile,
                                    dataInputs.password

                                )
                            ) {
                                Color.White
                            } else {
                                Color.Gray
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(all = 5.dp)

                            .clickable { navController.popBackStack() },
                        horizontalArrangement = Arrangement.Center

                    ) {

                        Row {
                            Text("Already have an account? ", fontSize = 13.sp)
                            Text(
                                " Click Here!", fontSize = 13.sp,
                                color = appThemeAccident,
                            )
                        }
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun register() {
    RegisterScreen(rememberNavController())
}



