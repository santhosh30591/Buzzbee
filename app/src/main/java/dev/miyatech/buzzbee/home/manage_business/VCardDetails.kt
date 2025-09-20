package dev.miyatech.buzzbee.home.manage_business


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import java.util.Date


@Composable
fun VCardDetailsScreen(navController: NavController) {

    var btn = 0
    var currentDateTime by remember {
        mutableStateOf("")
    }

    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = btn) {
        currentDateTime = " l " + Date().time.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White),
    ) {

        HomeTitleBarBack(text = " My Business ", navController)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)

                .background(Color.White),
        ) {

            Card(
                modifier = Modifier.padding(15.dp),
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth(), Alignment.Center

                ) {
                    Icon(
                        imageVector = Icons.Default.Person2,
                        contentDescription = "",
                        Modifier
                            .padding(20.dp)
                            .clip(CircleShape)
                            .border(width = 1.dp, appThemeAccident, shape = CircleShape)
                            .background(appThemeTintGray)
                            .padding(30.dp)
                            .size(50.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(
//                        text = "Santhosh Kumar R " + currentDateTime,
                        text = "Santhosh Kumar R ",
                        color = appThemePrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,

                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "santhoshkumar30591@gmail.com",
                        color = Color.Black,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "152",
                            color = appThemePrimary,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Offer's",
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)

                    ) {
                        Text(
                            text = "627",
                            color = appThemePrimary,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Following",
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)

                    ) {
                        Text(
                            text = "85",
                            color = appThemePrimary,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " Post ",
                            color = Color.Black,
                            fontSize = 14.sp,
                        )
                    }
                }
                Spacer(modifier = Modifier.heightIn(20.dp))


            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                        Text(
                            text = "Mobile Number",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.weight(1f),

                            )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "9483834427",

                            textAlign = TextAlign.End,
                            color = Color.Black,
                            fontSize = 13.sp,
                        )
                    }
                }


                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(appThemeTintGray)
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "WhatsApp",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,

                            )
                        Text(
                            text = "7648493842",
                            textAlign = TextAlign.End,
                            color = Color.Black,
                            fontSize = 13.sp,
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(appThemeTintGray)
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Email",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,

                            )
                        Text(
                            text = "santhoshkumar30591@gmail.com",
                            textAlign = TextAlign.End,
                            color = Color.Black,
                            fontSize = 13.sp,
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(appThemeTintGray)
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )


                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Address",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                            color = Color.Black

                        )
                        Text(
                            text = "No.86/205, First Floor, Shre Complex, Salem Rd, opposite AKR Parcel Service, R.P Pudur, Namakkal, Tamil Nadu 637001",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,

                            )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(appThemeTintGray)
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )


                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Description",
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                            color = Color.Black

                        )
                        Text(
                            text = "Buzzbee is an Indian technology-based marketing platform, operating through a mobile application, that connects local businesses with customers by providing deals, discounts, and promotional services. The app offers different services for customers, such as finding deals on various products and booking events, and for business owners to promote their offerings, manage events, and engage with customers through digital notifications",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,

                            )
                    }
                }


            }


        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {


            Button(
                onClick = {
                    btn += 2
                    currentDateTime = "n " + Date().time
                }, modifier = Modifier
                    .weight(1f), colors = ButtonDefaults.buttonColors(appThemePrimary80)

            ) {
                Text(text = "Share")
            }
        }


    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VCardDetailsScreen() {
    VCardDetailsScreen(navController = rememberNavController())

}