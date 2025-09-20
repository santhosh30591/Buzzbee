package dev.miyatech.buzzbee.home


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MapsUgc
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PhoneForwarded
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.model.BusinessDetailsResult
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.theme.BuzzbeeTheme
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui.alerts.ShowLoading
import dev.miyatech.buzzbee.ui_components.bounceClick
import dev.miyatech.buzzbee.viewmodel.HomeViewModel


@SuppressLint("MutableCollectionMutableState")
@Composable
fun BusinessDetails(navController: NavController, context: Context, id: String, category: String) {

    var viewModel = HomeViewModel()
    try {
        viewModel = ViewModelProvider(context as MainActivity).get(HomeViewModel::class.java)
    } catch (e: Exception) {
        println("business details error " + e)
    }


    var businessDetails by remember { mutableStateOf(BusinessDetailsResult()) }
    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("No Data Found") }


    LaunchedEffect(key1 = true) {
        viewModel.businessDetailsApi(context, "" + id)
        try {
            viewModel.details.observe(context as MainActivity) { response ->

                when (response) {
                    is NetworkResult.Loading -> {
                        isLoading = true
                    }

                    is NetworkResult.Success -> {
                        isLoading = false

                        val jsonString = Gson().toJson(response)
                        println("dddddddd " + jsonString)

                        if (response.data.errorCode == 0) {
                            errorMsg = "0"
                            businessDetails = response.data.result
                        } else {
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
        } catch (_: Exception) {

        }

    }

    Surface {


        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White),

                ) {


                HomeTitleBarBack(text = " " + category, navController)

                if (errorMsg.equals("0")) {
                    details(businessDetails, context, navController)
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(Color.White)
                            .clickable { viewModel.discoverLoading(context) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = errorMsg)
                    }
                    ShowLoading(isLoading = isLoading, submit = {

                    })

                }


            }


        }
    }
}


@Composable
fun details(
    business: BusinessDetailsResult,
    context: Context, navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val cardCornnerPading = 10
    val cardInsidePading = 10.dp
    val iconsSize = 30.dp
    val subIconsSize = 25.dp
    val subIconspading = 6.dp


    Column(

        modifier = Modifier
            .fillMaxWidth()

            .padding(cardInsidePading)
            .verticalScroll(rememberScrollState())
            .clickable {}) {


        Box(
            modifier = Modifier
                .size(
                    screenWidth, (configuration.screenWidthDp / 1.02).dp
                )
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = appThemeTintGray.copy(alpha = .5f),
                )


        ) {
            AsyncImage(
                model = business.bannerbg,
                contentDescription = "Background image",
                modifier = Modifier

                    .size(
                        screenWidth, screenWidth / 2F
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = (cardCornnerPading).dp, topEnd = (cardCornnerPading).dp
                        )
                    )
                    .background(Color.Gray),
                contentScale = ContentScale.Crop // Adjust content scale as needed
            )

            Column(

                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = (configuration.screenWidthDp / 3.2).dp)
                    .padding(all = cardInsidePading),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {


                Box(
                    modifier = Modifier
                        .size(
                            (configuration.screenWidthDp / 3.5).dp,
                            (configuration.screenWidthDp / 3.5).dp
                        )

                        .padding(all = cardInsidePading)
                        .clip(CircleShape)


                ) {
                    AsyncImage(
                        model = "" + business.imageUrl, // Replace with your image URL
                        contentDescription = "",
                        // Optional: Add placeholder and error drawables
                        placeholder = painterResource(id = R.drawable.placeholder_image),
                        error = painterResource(id = R.drawable.error_image),
                        // Optional: Customize contentScale, transformations, etc.
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.background(Color.Gray)
                    )
                }


                Text(
                    text = business.name, style = TextStyle(
                        fontSize = 17.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    ), maxLines = 2, fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = business.geoAddress,
                    style = TextStyle(
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.height(15.dp))


                Row(

                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp)
                        .height(50.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = appThemeTintGray.copy(alpha = .5f),
                        ), verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(iconsSize)
                            .bounceClick()
                            .clickable {

                            },
                        tint = appThemeAccident
                    )
                    Text(
                        text = "", modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(
                                color = appThemeTintGray.copy(alpha = .5f),
                            )
                    )

                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "call",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(iconsSize)
                            .bounceClick()
                            .clickable {

                            },
                        tint = appThemeAccident
                    )
                    Text(
                        text = "", modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(
                                appThemeTintGray.copy(alpha = .5f),
                            )
                    )

                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = "message",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(iconsSize)
                            .bounceClick()
                            .clickable {

                            },
                        tint = appThemeAccident
                    )
                    Text(
                        text = "", modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(
                                appThemeTintGray.copy(alpha = .5f),
                            )
                    )

                    Icon(
                        imageVector = Icons.Default.MapsUgc,
                        contentDescription = "chart",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(iconsSize)
                            .bounceClick()
                            .clickable {

                            },
                        tint = appThemeAccident
                    )

                    Text(
                        text = "", modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(
                                appThemeTintGray.copy(alpha = .5f),
                            )
                    )


                    Icon(
                        imageVector = Icons.Default.LocationSearching,
                        contentDescription = "chart",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(iconsSize)
                            .bounceClick()
                            .clickable {

                            },
                        tint = appThemeAccident
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(cardInsidePading))

        Box(
            modifier = Modifier

                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = appThemeTintGray.copy(alpha = .5f),
                )


        ) {

            Column(


                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = cardInsidePading),


                ) {


                Text(
                    text = " Communication",
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    ),
                    maxLines = 2,
                )
                Spacer(Modifier.height(15.dp))



                Column(

                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)

                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .bounceClick()
                            .clickable { }) {
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp,
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White // Set your desired color here
                            ),

                            ) {

                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "email",
                                modifier = Modifier
                                    .padding(all = subIconspading)
                                    .size(subIconsSize)

                                    .clickable {

                                    },
                                tint = Color(0xFFff641a)
                            )

                        }
                        Text(
                            text = "" + business.email,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(all = subIconspading)
                        )


                    }

                    Spacer(Modifier.height(18.dp))
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .bounceClick()
                            .clickable { }) {
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp,
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White // Set your desired color here
                            ),

                            ) {

                            Icon(
                                imageVector = Icons.Default.PhoneForwarded,
                                contentDescription = "call",
                                modifier = Modifier
                                    .padding(all = subIconspading)
                                    .size(subIconsSize)

                                    .clickable {

                                    },
                                tint = Color(0xFF0370A2)
                            )

                        }
                        Text(
                            text = "" + business.mobile,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(all = subIconspading)
                        )


                    }

                    Spacer(Modifier.height(18.dp))
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .bounceClick()
                            .clickable { }) {
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp,
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White // Set your desired color here
                            ),

                            ) {

                            Icon(
                                imageVector = Icons.Default.MapsUgc,
                                contentDescription = "what shap",
                                modifier = Modifier
                                    .padding(all = subIconspading)
                                    .size(subIconsSize)

                                    .clickable {

                                    },
                                tint = Color(0xFF23AC23)
                            )

                        }
                        Text(
                            text = "" + business.whatsapp,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(all = subIconspading)
                        )


                    }

                    Spacer(Modifier.height(18.dp))

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .bounceClick()
                            .clickable { }) {
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp,
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White // Set your desired color here
                            ),

                            ) {

                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "web",
                                modifier = Modifier
                                    .padding(all = subIconspading)
                                    .size(subIconsSize)

                                    .clickable {

                                    },


                                tint = Color(0xFFff641a)

                            )

                        }
                        Text(
                            text = "" + business.website,
                            color = Color.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(all = subIconspading)
                        )
                    }

                    Spacer(Modifier.height(5.dp))


                }
            }
        }

        Spacer(modifier = Modifier.height(cardInsidePading))

        Box(
            modifier = Modifier

                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = appThemeTintGray.copy(alpha = .5f),
                )


        ) {

            Column(


                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = cardInsidePading),


                ) {


                Text(
                    text = " Address",
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    ),
                    maxLines = 2,
                )
                Spacer(Modifier.height(5.dp))

                Column(
                    modifier = Modifier.padding(start = 5.dp)

                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .bounceClick()
                            .clickable { }) {

                        Text(
                            text = "" + business.geoLocation,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)
                        )

                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp,
                            ),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White // Set your desired color here
                            ),

                            ) {

                            Icon(
                                imageVector = Icons.Default.Map,
                                contentDescription = "email",
                                modifier = Modifier

                                    .size(50.dp),

                                tint = appThemePrimary
                            )

                        }
                    }



                    Spacer(Modifier.height(5.dp))


                }
            }
        }


    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessDetails() {
    BuzzbeeTheme {
        val context = LocalContext.current
        details(details1(), context, rememberNavController())

//        BusinessDetails(rememberNavController(), context, "18", "Sample title")
    }
}


fun details1(): BusinessDetailsResult {
    return BusinessDetailsResult(
        "id 18",
        "name MSR Enginner",
        "reg_number MSRENG486342",
        "type 1",
        "status 1",
        "categoryid 21,23",
        "categoryname Books, Food",
        "subcategoryid 1",
        "mobile 9876565678",
        "alt_mobile 7673829331",
        "whatsapp 9876565678",
        "email sathishr2394@gmail.com",
        "website www.msreng.com",
        "address1 Marathallai",
        "address2 Pertol Bunk",
        "address3 School",
        "locality Marathallai Locality",
        "area Marathallai Area",
        "taluk Marathallai Post",
        "pincode 560103",
        "landmark Waycool",
        "stateid 2",
        "districtid 4",
        "geo_address salem",
        "geo_location Sangagiri, Sangagiri, Tamil Nadu, India",
        "latitude 11.4744536",
        "longitude 77.8691280",
        "inc_name Santhosh",
        "inc_designation MD",
        "inc_mobile 8838647931",
        "inc_whatsapp 8838647931",
        "inc_email sathishr2394@gmail.com",
        "business_id BB000018",
        "imageUrl http://miyahosting.co.in/buzzbee/portal/uploads/JdzRcJzHivthcxvr.png"

    )

}