package dev.miyatech.buzzbee.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.HomeBottomViews
import dev.miyatech.buzzbee.bottomNavItemsIcons
import dev.miyatech.buzzbee.ui.theme.appTheme
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary


@Composable
fun HomeMainScreen(mainNavControllers: NavHostController) {
    val navController = rememberNavController()
//    val navController = navControllers


    MaterialTheme(colorScheme = appTheme) {

//        val view = LocalView.current
//        val window = (view.context as Activity).window
//        window.statusBarColor = appThemePrimary.toArgb()

        Surface(color = Color.White) {
            // Scaffold Component
            Scaffold(
                // Bottom navigation
                bottomBar = {

                    BottomNavigationBar(navController = navController)
                }, content = { padding ->
                    // Nav host: where screens are placed
                    HomeBottomViews(
                        navController = navController,
                        padding = padding,
                        mainNavControllers
                    )
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(
        modifier = Modifier.height(55.dp),
        containerColor = appThemePrimary,

        ) {


        var selected by remember { mutableStateOf(false) }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        bottomNavItemsIcons.forEach { navItem ->
            selected = currentRoute == navItem.route

            Column(
                modifier = Modifier
                    .padding(all = 6.dp)
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {

                        navController.navigate(navItem.route) {
                            popUpTo(currentRoute!!) {
                                inclusive =
                                    true // This removes the splash screen from the back stack
                            }
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center


            ) {

                Icon(
                    painterResource(id = navItem.icon),
                    contentDescription = navItem.label,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(all = 2.dp),
                    tint = if (selected) appThemeAccident else Color.White,
                )

                Text(
                    text = navItem.label,
                    color = if (selected) appThemeAccident else Color.White,
                    fontSize = 11.sp,
                )
            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeMainScreen() {
    HomeMainScreen(rememberNavController())
}