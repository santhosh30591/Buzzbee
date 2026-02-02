package dev.miyatech.buzzbee.home

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.model.DashboardLiveResult
import dev.miyatech.buzzbee.model.DashboardSlides
import dev.miyatech.buzzbee.navDrawerSideMenu
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.alerts.ShowLoading
import dev.miyatech.buzzbee.ui.alerts.ShowToast
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemeCardTint
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.BottomTabLineView
import dev.miyatech.buzzbee.ui_components.DBHelper
import dev.miyatech.buzzbee.ui_components.HomeTitleBar
import dev.miyatech.buzzbee.ui_components.ImageSlider
import dev.miyatech.buzzbee.ui_components.bounceClick
import dev.miyatech.buzzbee.ui_components.loadingSlideShow
import dev.miyatech.buzzbee.ui_components.shareString
import dev.miyatech.buzzbee.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


@Composable
fun HomeScreen(
    navController: NavController,
    title: String,
    pos: Int,
    mainNavControllers: NavHostController,
    viewModel: HomeViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var showSuccessAlerts by remember { mutableStateOf(false) }


    var notificationCount by remember { mutableStateOf("") }

    var discoverList by remember { mutableStateOf(ArrayList<DashboardSlides>()) }



    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf(" ") }
    var ads_banner by remember { mutableStateOf("") }

    LaunchedEffect(key1 = null) {
        context as MainActivity




        try {
            viewModel.dashboard.observe(context) { response ->

                when (response) {
                    is NetworkResult.Loading -> {
                        if (discoverList.size == 0) {
                            isLoading = true
                        }
                    }

                    is NetworkResult.Success -> {
                        isLoading = false

                        ads_banner =
                            response.data.results.ads_banner

                        if (response.data.results.slides.size != 0) {
                            discoverList.clear()
                            discoverList.addAll(response.data.results.slides)


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

        try {
            viewModel.dashboardLive.observe(context) { response ->
                notificationCount = response.notification_count.toString()
            }
        } catch (e: Exception) {
            println(" loading error ")
            errorMsg = " " + e
        }
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier.width(280.dp)
        ) {
            var name by remember { mutableStateOf("") }
            try {
                name = DBHelper(context as MainActivity).loginGetDetails().name.toString()
            } catch (e: Exception) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // HEADER - TITLE
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                // NAVIGATION ITEMS
                navDrawerSideMenu.forEachIndexed { index, sideMenus ->
                    NavigationDrawerItem(icon = {
                        Image(

                            imageVector = sideMenus.icon,
                            contentDescription = sideMenus.contentDescription,
                            colorFilter = ColorFilter.tint(appThemePrimary)
                        )
                    }, label = {
                        Text(
                            text = sideMenus.title,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.W400
                        )
                    }, selected = false, onClick = {
                        coroutineScope.launch { drawerState.close() }
                        if (index == 0) {
                            mainNavControllers.navigate(Screen.Profile.route)
                        } else if (index == 1) {
                            mainNavControllers.navigate(Screen.ManageBusiness.route)
                        } else if (index == 2) {
                            mainNavControllers.navigate(Screen.Notification.route)
                        } else if (index == 3) {
                            shareString(context, "this share sample message ")
                        } else if (index == 4) {
                            var uriString = ""
                            var org =
                                "https://miyahosting.co.in/buzzbee/portal/controllers/terms.html"
                            try {
                                uriString = URLEncoder.encode(org, "UTF-8")
                                println("Encoded string (UTF-8, String enc): $uriString")
                            } catch (e: UnsupportedEncodingException) {
                                System.err.println("Encoding not supported: " + e.message)
                            }
                            mainNavControllers.navigate(Screen.Terms.route + "/How is Working/$uriString")

                        } else if (index == 5) {

                            var uriString = ""
                            var org =
                                "https://miyahosting.co.in/buzzbee/portal/controllers/terms.html"
                            try {
                                uriString = URLEncoder.encode(org, "UTF-8")
                                println("Encoded string (UTF-8, String enc): $uriString")
                            } catch (e: UnsupportedEncodingException) {
                                System.err.println("Encoding not supported: " + e.message)
                            }
                            mainNavControllers.navigate(Screen.Terms.route + "/Terms/$uriString")

                        } else {
                            showSuccessAlerts = true
                            showToast = true

                        }
                    })   // navigation-drawer-item
                }

            }   // column
        }   // model-drawer-sheet
    }) {
        Scaffold(topBar = {
            if (title.equals("Home")) {
                CenterAlignedToolbar(
                    navController,
                    notificationCount,
                    drawerState,
                    coroutineScope,
                    mainNavControllers
                )
            } else {
                HomeTitleBar(text = title)
            }

        }) {


            Box(Modifier.padding(it)) {
                Home1(title, pos, context, discoverList, ads_banner, viewModel)

            }
        }

        if (showSuccessAlerts) {
            ShowToast(
                onDismissCloseIcons = {
                    showSuccessAlerts = false
                }, onDismiss = {
                    showSuccessAlerts = false
                    DBHelper(context as MainActivity).saveLogin(
                        logins = ""
                    )

                    mainNavControllers.navigate(Screen.Splash.route) {
                        popUpTo(Screen.HomeMain.route) {
                            inclusive = true // This removes the splash screen from the back stack
                        }
                    }
                }, outSide = {}, title = "Logout", subTitle = "Are you sure you want to log out?"
            )
        }
        ShowLoading(isLoading = isLoading) {

        }
        ShowToast(onDismiss = {
            showToast = false
        }, title = "", subTitle = errorMsg, outSide = {

        }, showToast = showToast, onDismissCloseIcons = {
            showToast = false
        })
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home1(

    title: String,
    pos: Int,
    context: Context,
    sliderList: ArrayList<DashboardSlides>,
    ads_banner: String, viewModel: HomeViewModel = HomeViewModel()

) {

//
//
    var late by remember { mutableStateOf(0.0) }

//
//    val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
//    val loca = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
//    val isGpsEnabled = remember { mutableStateOf(loca) }
//
//
//    val locationPermissions = arrayOf(
//        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
//    )
//
//    val locationPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions(),
//        onResult = { permissions ->
//            val permissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
//                acc && isPermissionGranted
//            }
//
//            if (!permissionsGranted) {
//
//            }
//        })
//
//
//    val settingResultRequest = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) {
//
//
//        isGpsEnabled.value =
//            locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
//
//        if (isGpsEnabled.value) {
//
//        } else {
//
//        }
//        Log.i(
//            "ammu",
//            "TEST 1 : ${locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false}"
//        )
//    }
//    LaunchedEffect(key1 = null) {
//        try {
////            late = location.latitude
//            locationPermissionLauncher.launch(locationPermissions)
//            viewModel.latelong.observe(context as MainActivity, {
//                late = it.latitude
//            })
//
//        } catch (e: Exception) {
//        }
//    }

    var liveDataResule by remember { mutableStateOf(DashboardLiveResult()) }

    LaunchedEffect(key1 = null) {

        viewModel.dashboardLive.observe(context as MainActivity, {
            liveDataResule = it
        })

    }


    Surface {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White)


            ) {
                Column(
                    modifier = Modifier

                        .fillMaxSize()
                        .background(Color.White)
//                .verticalScroll(rememberScrollState()),

                ) {



                    Box(
                        modifier = Modifier.background(Color.LightGray)
                        // .verticalScroll(rememberScrollState())
//                    .padding(32.dp)
                    ) {

                        val scrollState = rememberScrollState()

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(scrollState)
                                .background(Color.White)
                                .clickable {
//                        val locationManager =
//                            context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
//                        val loca = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                            ?: false
//                        isGpsEnabled.value = loca
//                        if (isGpsEnabled.value) {
//                            var gps = LocationTrack(context)
//                            late = 1.0;
//                            try {
//                                late = gps.location.latitude
//                            } catch (e: Exception) {
//                                late = 1.0;
//                                println("its loafing $e")
//                            }
//                        } else {
//                            settingResultRequest.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//                        }
                                },
                        ) {

                            BasicPieChart(liveDataResule)

                            if (sliderList.size != 0) {
                                loadingSlideShow(sliderList)
                                Spacer(modifier = Modifier.height(5.dp))
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
//                            .clip(RoundedCornerShape(10.dp)),
//                            .background(appThemeAccident50)
                                    Alignment.TopStart,
                                ) {
                                    Text(
                                        text = "Category",
                                        fontSize = 15.sp
                                    )
                                }

                                loadingHome1(sliderList, context, rememberNavController())
//                                PieChartWithLabels()
//                                Box(
//                                    Modifier
//                                        .fillMaxWidth()
//                                        .padding(8.dp)
//
//                                        .background(appThemeAccident50)
//                                        .padding(25.dp),
//                                    Alignment.Center,
//                                ) {

                                if (ads_banner.length != 0) {
                                    AsyncImage(
//                        model = images[index].imageUrl, // Replace with your image URL
                                        model = ads_banner,
                                        contentDescription = "",
                                        // Optional: Add placeholder and error drawables
                                        placeholder = painterResource(id = R.drawable.placeholder_image),
                                        error = painterResource(id = R.drawable.error_image),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(15.dp)
                                            .clip(RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                        // Optional: Customize contentScale, transformations, etc.

                                    )
                                }

                                ImageSlider(images = sliderList)


                            } else {
//                                Box(
//                                    Modifier
//                                        .fillMaxWidth()
//                                        .padding(8.dp)
//                                        .clip(RoundedCornerShape(10.dp))
//                                        .background(appThemeAccident50)
//                                        .padding(40.dp),
//                                    Alignment.Center,
//                                ) {
//                                    Text(text = title + " Location " + late)
//                                }

                            }
                        }
                    }

                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BottomTabLineView(posi = pos)
            }

        }
    }


}

@Composable
fun loadingHome1(
    discoverList: ArrayList<DashboardSlides>, context: Context, navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyVerticalGrid(

        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)

    ) {

        items(discoverList.size) { position ->

            Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()

                    .clickable {


                    }) {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(5.dp)
                        )
                        .background(appThemeCardTint),

                    horizontalAlignment = Alignment.CenterHorizontally,


                    ) {


                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((screenWidth / 6F))


                    ) {

                        var images = ""
                        try {
                            images = discoverList[position].imageUrl.replace(
                                "http://", "https://"
                            )
                        } catch (e: Exception) {

                        }
                        AsyncImage(
                            model = images, // Replace with your image URL
                            contentDescription = "",
                            // Optional: Add placeholder and error drawables
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            error = painterResource(id = R.drawable.error_image),
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                            // Optional: Customize contentScale, transformations, etc.

                        )
                    }

                    Text(
                        text = discoverList[position].name,
                        style = TextStyle(
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(5.dp)

                    )
                }
            }

        }
    }
}


@Composable
fun BasicPieChart(result: DashboardLiveResult) {

    var tot = result.max_val + result.min_val

    println("total $tot ")

    var a1: Float = 0f
    var b1: Float = 0f


    try {
        var a = ((result.max_val.toDouble() * 100) / tot) / 100
        var b = ((result.min_val.toDouble() * 100) / tot) / 100

        a1 = a.toFloat()
        b1 = b.toFloat()

    } catch (e: Exception) {
        println("error $e ")
    }


    val entries = listOf(
        PieChartEntry(appThemePrimary, a1),
//        PieChartEntry(Color.Green, 0.3f),
        PieChartEntry(appThemeAccident, b1)
    )
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PieChart(entries)
        Column(
            modifier = Modifier
                .padding(10.dp)

                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp),

            ) {
            Text("Total Post " + result.max_val, color = appThemePrimary, fontSize = 16.sp)
            Text("Live Post " + result.min_val, color = appThemeAccident, fontSize = 16.sp)
            Text("Date " + result.date_time, color = appThemeTintGray, fontSize = 12.sp)


        }
    }
    Text(
        "News " + result.news, color = Color.White,
        modifier = Modifier
            .background(appThemePrimary80)
            .padding(15.dp)
            .fillMaxWidth(),
    )
}

data class PieChartEntry(val color: Color, val percentage: Float)

fun calculateStartAngles(entries: List<PieChartEntry>): List<Float> {
    var totalPercentage = 0f
    return entries.map { entry ->
        val startAngle = totalPercentage * 360
        totalPercentage += entry.percentage
        startAngle
    }
}

@Composable
fun PieChart(entries: List<PieChartEntry>) {


    Canvas(
        modifier = Modifier
            .size(150.dp)
            .fillMaxWidth()
    ) {

        val startAngles = calculateStartAngles(entries)
        entries.forEachIndexed { index, entry ->
            drawArc(

                color = entry.color,
                startAngle = startAngles[index],
                sweepAngle = entry.percentage * 360f,
                useCenter = true,
                topLeft = Offset.Zero,
                size = this.size
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    HomeScreen(rememberNavController(), "", 1, rememberNavController(), HomeViewModel())
}