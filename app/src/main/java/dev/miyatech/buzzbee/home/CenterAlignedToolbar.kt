package dev.miyatech.buzzbee.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedToolbar(
    navController: NavController,
    notifyCount: String,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    mainNavControllers: NavHostController,

    ) {


    var count = remember { mutableStateOf(notifyCount) }



    LaunchedEffect(key1 = true) {


    }


    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Buzzbee " + count.value,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(appThemePrimary),// title
        navigationIcon = {
            Image(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Navigation menu",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(31.dp)
                    .clickable {
                        coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
            )

        },

        actions =
            {
                ShoppingCartIconWithBadge(notifyCount, mainNavControllers)
                Spacer(Modifier.width(15.dp))
            }
    )   // center-aligned-top-app-bar
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartIconWithBadge(itemCount: String, mainNavControllers: NavHostController) {
    BadgedBox(
        modifier = Modifier.clickable() {
            mainNavControllers.navigate(Screen.Notification.route)
        },
        badge = {
            if (itemCount.length != 0) { // Only show badge if count is greater than 0
                Badge {
                    Text(text = itemCount.toString(), color = Color.White)
                }
            }
        }

    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "notify cart", tint = Color.White
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShoppingCartIconWithBadge() {
    ShoppingCartIconWithBadge("4", rememberNavController())
}