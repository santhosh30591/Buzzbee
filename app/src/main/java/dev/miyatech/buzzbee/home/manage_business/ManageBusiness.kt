package dev.miyatech.buzzbee.home.manage_business

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.ui.theme.appThemeCardTint
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui_components.bounceClick
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@Composable
fun ManageBusiness(navController: NavController) {

    val scroll = rememberScrollState()
    Surface {
        Column(
            modifier = Modifier.background(Color.White),
        ) {
            HomeTitleBarBack(text = "Manage Business", navController)
            Column(
                modifier = Modifier.verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(appThemePrimary)
                        .background(Color.White)
                        .padding(start = 15.dp, end = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {

                    Spacer(modifier = Modifier.height(15.dp))
                    CardView(Icons.Default.Business, "My Business", onclick = {

                    })

                    CardView(Icons.Default.LocalOffer, "Subscription Plans", onclick = {

                    })

                    CardView(Icons.Default.PostAdd, "Create Post", onclick = {
                        navController.navigate(Screen.CreatePost.route)
                    })

                    CardView(Icons.Default.AddBusiness, "Share My Business", onclick = {
                        navController.navigate(Screen.VCard.route)

                    })

                    CardView(Icons.Default.Help, "Help & Support", onclick = {


                        var uriString = ""
                        var org =
                            "https://localkart.app/help-support.php"
                        try {
                            uriString = URLEncoder.encode(org, "UTF-8")
                            println("Encoded string (UTF-8, String enc): $uriString")
                        } catch (e: UnsupportedEncodingException) {
                            System.err.println("Encoding not supported: " + e.message)
                        }

                        navController.navigate(Screen.Terms.route + "/Customer Support/$uriString")

                    })
                }
            }
        }
    }
}

@Composable
fun CardView(imageVector: ImageVector, title: String, onclick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .bounceClick()
        .border(
            width = 1.dp, color = appThemeCardTint, shape = RoundedCornerShape(10.dp)
        )
        .padding(10.dp)
        .clickable { onclick() }
        .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = imageVector,
            contentDescription = title,
            colorFilter = ColorFilter.tint(appThemePrimary),
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = title,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManageBusiness() {
    ManageBusiness(navController = rememberNavController())
}