package dev.miyatech.buzzbee.home


import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui_components.DBHelper
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui.alerts.ShowToast


@Composable
fun ProfileScreen(navController: NavController) {


    var versions = "1.0.0"

    val context = LocalContext.current

    var name = ""
    var mobile = ""
    var address = ""
    try {
        versions = getAppVersion(context)
        name = DBHelper(context as MainActivity).loginGetDetails().name.toString()
        mobile = DBHelper(context as MainActivity).loginGetDetails().mobile.toString()
        address =
            DBHelper(context as MainActivity).loginGetDetails().districtname.toString() + ", " + DBHelper(
                context as MainActivity
            ).loginGetDetails().cityname.toString()
    } catch (e: Exception) {

    }


    var showLogout by remember { mutableStateOf(false) }


    Surface {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White),
        ) {

            HomeTitleBarBack(text = " Profile ", navController)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(appThemePrimary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                BoxWithConstraints(
                    Modifier
//                            .background(color = Color.Gray)
                        .fillMaxSize()
                    //  .padding(20.dp)
                ) {


                    Column(


                        modifier = Modifier
//                                .width(boxWidth - 10.dp)

                            .fillMaxSize()
                            .padding(top = 100.dp)
                            .background(Color.White),


                        horizontalAlignment = Alignment.CenterHorizontally


                    ) {


                        Column(

                            modifier = Modifier
                                .background(appThemePrimary)
                                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))

                                .fillMaxWidth()
                                .background(Color.White),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {

                            Spacer(modifier = Modifier.height(50.dp))

                            Text(
                                name, modifier = Modifier.padding(all = 14.dp),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    imageVector = Icons.Default.Mail,
                                    contentDescription = "back",
                                    colorFilter = ColorFilter.tint(appThemePrimary),
                                    modifier = Modifier
                                        .padding(start = 18.dp)
                                        .size(20.dp)

                                )

                                Text(
                                    text = "santhoshkumar30591@gmail.com",
                                    color = Color.Black,

                                    modifier = Modifier.padding(all = 10.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "back",
                                    colorFilter = ColorFilter.tint(appThemePrimary),
                                    modifier = Modifier
                                        .padding(start = 18.dp)
                                        .size(20.dp)

                                )

                                Text(
                                    text = "" + mobile,
                                    color = Color.Black,
                                    modifier = Modifier.padding(all = 10.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    imageVector = Icons.Default.LocationCity,
                                    contentDescription = "back",
                                    colorFilter = ColorFilter.tint(appThemePrimary),
                                    modifier = Modifier
                                        .padding(start = 18.dp)
                                        .size(20.dp)

                                )

                                Text(
                                    text = "" + address,
                                    color = Color.Black,

                                    modifier = Modifier.padding(all = 10.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "back",
                                    colorFilter = ColorFilter.tint(appThemePrimary),
                                    modifier = Modifier
                                        .padding(start = 18.dp)
                                        .size(20.dp)

                                )

                                Text(
                                    text = "Share",
                                    color = Color.Black,

                                    modifier = Modifier.padding(all = 10.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable {
                                        showLogout = true

                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "back",
                                    colorFilter = ColorFilter.tint(appThemePrimary),
                                    modifier = Modifier
                                        .padding(start = 18.dp)
                                        .size(20.dp)

                                )

                                Text(
                                    text = "Logout",
                                    color = Color.Black,

                                    modifier = Modifier.padding(all = 10.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }


                            if (showLogout) {
                                ShowToast(
                                    outSide = {

                                    }, onDismissCloseIcons = {
                                        showLogout = false
                                    },
                                    onDismiss = {
                                        showLogout = false
                                        DBHelper(context as MainActivity).saveLogin(
                                            logins = ""
                                        )
                                        navController.navigate(Screen.Splash.route) {
                                            popUpTo(Screen.Profile.route) {
                                                inclusive =
                                                    true // This removes the splash screen from the back stack
                                            }
                                        }
                                    },
                                    title = "Logout",
                                    subTitle = "Are you sure you want to log out?"
                                )
                            }

                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(top = 50.dp)
//                                .width(boxWidth - 10.dp)
                            .fillMaxWidth()

                    ) {
                        Box(
                            modifier = Modifier
//                                .width(boxWidth - 10.dp)
                                .width(100.dp)

                                .height(100.dp)
                                .clip(RoundedCornerShape(size = 49.dp))
                                .background(Color.Gray)
//                                    .clip(RoundedCornerShape(bottomStart = 90.dp, topStart = 90.dp, bottomEnd = 90.dp, topEnd = 90.dp))
                                .clip(RoundedCornerShape(size = 50.dp))
                                .background(Color.White),

                            contentAlignment = Alignment.Center,


                            ) {

                            Icon(
                                imageVector = Icons.Default.Person2,
                                contentDescription = "profiles",
                                modifier = Modifier.size(50.dp),
                                tint = appThemeAccident
                            )
                        }
                    }

                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),

                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Version " + versions,
                    color = Color.Black,


                    modifier = Modifier
                        .background(Color.White)
                        .padding(all = 15.dp),


                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

fun getAppVersion(context: Context): String {


    try {

        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return "Unknown" // Or handle the error as appropriate
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun profilesScreen() {


    ProfileScreen(navController = rememberNavController())

}