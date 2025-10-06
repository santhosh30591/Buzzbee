package dev.miyatech.buzzbee.home


import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.model.NotificationResult
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.alerts.ShowToast
import dev.miyatech.buzzbee.ui.alerts.shimmerLoadingAnimation
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80
import dev.miyatech.buzzbee.ui_components.DBHelper
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui_components.bounceClick
import dev.miyatech.buzzbee.ui_components.dateFormateYMD_HMA
import dev.miyatech.buzzbee.viewmodel.HomeViewModel

@Composable
fun NotificationScreen(navController: NavController, context: Context) {
    var isLoading by remember { mutableStateOf(false) }
    var notificationList by remember { mutableStateOf(arrayListOf<NotificationResult>()) }

    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf(" ") }

    var viewmodel = HomeViewModel()
    var shouldFetch by remember { mutableStateOf(true) }


    LaunchedEffect(shouldFetch) {
        context as MainActivity

        if (shouldFetch) {

            viewmodel = ViewModelProvider(context as MainActivity).get(HomeViewModel::class.java)
        }

        viewmodel.getNotificatiopnList(context, DBHelper(context).loginGetDetails().id.toString())

        try {
            viewmodel.notificationList.observe(context) { response ->

                when (response) {
                    is NetworkResult.Loading -> {
                        if (notificationList.size == 0) {
                            isLoading = true
                            shouldFetch = false
                        }

                    }

                    is NetworkResult.Success -> {
                        isLoading = false
                        if (response.data.results.size != 0) {
                            notificationList.clear()
                            notificationList.addAll(response.data.results)
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
        } catch (e: Exception) {
            println(" loading error ")
            errorMsg = " " + e
        }

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            HomeTitleBarBack(text = "Notifications", navController)


            if (!isLoading) {


                if (notificationList.size == 0) {

                Box(
                    modifier = Modifier.fillMaxSize(), // Make the Box fill the available space
                    contentAlignment = Alignment.Center // Center its content both horizontally and vertically
                ) {


                    Card() {
                        Column(
                            modifier = Modifier.padding(all = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var expended by remember() {
                                mutableStateOf(true)
                            }
                            Image(
                                painterResource(id = R.drawable.error_image),
                                contentDescription = "",
                                modifier = Modifier.clickable { expended = !expended }
                            )
                            AnimatedVisibility(visible = expended) {
                                Text(
                                    text = "No Data Found . ",
                                    fontSize = 18.sp,
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }


                        }
                    }

                }
                } else {
                    NotificationDetails(notificationList, navController)
                }
            } else {
                LoadingView(isLoading)
            }
        }

        ShowToast(onDismiss = {
            showToast = false
        }, title = "", subTitle = errorMsg, outSide = {

        }, showToast = showToast, onDismissCloseIcons = {
            showToast = false
        })
    }


}


@Composable
fun NotificationDetails(
    discoverList: ArrayList<NotificationResult>, navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(15.dp),

        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        items(discoverList.size) { position ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .clickable {

                        navController.navigate(
                            Screen.NotificationsDetails.route + "/" + discoverList.get(
                                position
                            ).id
                        )
                    }
                    .border(width = 1.dp, color = appThemePrimary80, shape = RoundedCornerShape(5)),
            ) {

                Row(
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp))
                        .background(appThemePrimary)
                        .padding(6.dp),

                    ) {

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = discoverList[position].title.toString(), style = TextStyle(
                            fontSize = 14.sp,

                            color = Color.White,

                            ),

                        maxLines = 1
                    )

                    Text(
                        text = dateFormateYMD_HMA(discoverList[position].scheduledDate.toString() + " " + discoverList[position].scheduledTime.toString()),
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = Color.White,
                        ),
                        maxLines = 1
                    )
                }


                Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {


                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .size(
                                screenWidth / 8.5F, screenWidth / 8.5F
                            )
                            .padding(all = 2.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.1F), shape = CircleShape
                            )
                    ) {
                        AsyncImage(
                            model = "" + discoverList[position].photo, // Replace with your image URL
                            contentDescription = "",
                            modifier = Modifier

                                .clip(RoundedCornerShape(5.dp)),

                            // Optional: Add placeholder and error drawables
                            placeholder = painterResource(
                                id =
                                    R.drawable.placeholder_image
                            ),
                            error = painterResource(id = R.drawable.error_image),
                            // Optional: Customize contentScale, transformations, etc.
                            contentScale = ContentScale.Crop,

                            )
                    }

                    //Profile image


                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp),


                        ) {

                        Text(
                            text = discoverList[position].description.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,

                                color = Color.Black,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun LoadingView(isLoading: Boolean) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(6) { item ->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .shimmerLoadingAnimation(isLoading)
                    .padding(all = 5.dp)

                    .border(
                        shape = RoundedCornerShape(8.dp), color = Color.LightGray, width = 1.dp
                    )

            ) {

                Row(
                    Modifier.padding(all = 10.dp), verticalAlignment = Alignment.CenterVertically

                ) {

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = Color.LightGray)
                            .size(height = 40.dp, width = 40.dp)
                            .shimmerLoadingAnimation(
                                isLoading
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    Column() {
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(4.dp))
                                .background(color = Color.LightGray)
                                .height(height = 25.dp)
                                .fillMaxWidth()
                                .shimmerLoadingAnimation(
                                    isLoading
                                )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(4.dp))
                                .background(color = Color.LightGray)
                                .size(200.dp, 15.dp)
                                .shimmerLoadingAnimation(
                                    isLoading
                                )
                        )
                    }


                }

            }


        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreen() {
    NotificationScreen(navController = rememberNavController(), context = LocalContext.current)
}