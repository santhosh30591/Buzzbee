package dev.miyatech.buzzbee

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.home.BusinessDetails
import dev.miyatech.buzzbee.home.BusinessList
import dev.miyatech.buzzbee.home.DicoverScreen
import dev.miyatech.buzzbee.home.ExploerScreen
import dev.miyatech.buzzbee.home.HomeMainScreen
import dev.miyatech.buzzbee.home.HomeScreen
import dev.miyatech.buzzbee.home.LocationPermission
import dev.miyatech.buzzbee.home.NotificationScreen
import dev.miyatech.buzzbee.home.OfferScreen
import dev.miyatech.buzzbee.home.PreviewScreen
import dev.miyatech.buzzbee.home.ProfileScreen
import dev.miyatech.buzzbee.home.SubCategory
import dev.miyatech.buzzbee.home.VideoScreen
import dev.miyatech.buzzbee.home.WebViewsScreen
import dev.miyatech.buzzbee.home.manage_business.CreatePost
import dev.miyatech.buzzbee.home.manage_business.ManageBusiness
import dev.miyatech.buzzbee.home.manage_business.SupportCustomer
import dev.miyatech.buzzbee.home.manage_business.VCardDetailsScreen
import dev.miyatech.buzzbee.login.LoginScreen
import dev.miyatech.buzzbee.login.RegisterScreen
import dev.miyatech.buzzbee.login.SplashScree
import dev.miyatech.buzzbee.ui.ZoomImage
import dev.miyatech.buzzbee.viewmodel.HomeViewModel
import dev.miyatech.buzzbee.viewmodel.ManageByBusinessViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController() //  NavController to manage navigation
    val context = LocalContext.current

    var viewModel = ManageByBusinessViewModel()
    try {
        viewModel =
            ViewModelProvider(context as MainActivity).get(ManageByBusinessViewModel::class.java)
    } catch (_: Exception) {
    }
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route //  Start screen of the app
//        startDestination = Screen.BusinessDetails.route //  Start screen of the app
    ) {
        composable(Screen.Splash.route) { //  Define the Home screen
            SplashScree(navController)
        }
        composable(Screen.Login.route) { //  Define the Home screen
            LoginScreen(navController)
        }
        composable(Screen.Register.route) { //  Define the Settings screen
            RegisterScreen(navController)
        }
        composable(Screen.HomeMain.route) { //  Define the Home screen
            HomeMainScreen(navController)
        }

        composable(Screen.Profile.route) { //  Define the Profile screen
            ProfileScreen(navController)
        }
        composable(Screen.Notification.route) { //  Define the Profile screen
            NotificationScreen(navController)
        }

        composable(Screen.Location1.route) { //  Define the Settings screen
            LocationPermission(navController)
        }

        composable(Screen.ManageBusiness.route) { //  Define the Settings screen
            ManageBusiness(navController)
        }

        composable(Screen.CreatePost.route) { //  Define the Settings screen
            CreatePost(navController, viewModel)
        }
        composable(Screen.Support.route) { //  Define the Settings screen
            SupportCustomer(navController)
        }
        composable(Screen.VCard.route) { //  Define the Settings screen
            VCardDetailsScreen(navController)
        }

        composable(Screen.BusinessDetails.route + "/{title}/{id}") { navBackStack ->
            val title = navBackStack.arguments?.getString("title").toString()
            val id = navBackStack.arguments?.getString("id").toString()
            BusinessDetails(navController, context, id, title)
        }


        composable(Screen.Terms.route + "/{title}/{url}") { navBackStack ->

            val title = navBackStack.arguments?.getString("title").toString()
            val url = navBackStack.arguments?.getString("url").toString()
            WebViewsScreen(navController, url, title)
        }

        composable(Screen.ZoomingImg.route + "/{uri}") { navBackStack ->
            val uri = navBackStack.arguments?.getString("uri").toString()
            ZoomImage(navController, uri)
        }
        composable(Screen.SubCategory.route + "/{title}/{id}") { navBackStack ->
            // Extracting the argument
            val title = navBackStack.arguments?.getString("title").toString()
            val id = navBackStack.arguments?.getString("id").toString()
            SubCategory(navController, context, id, title)
        }

        composable(Screen.BusinessList.route + "/{title}/{id}") { navBackStack ->
            val category = navBackStack.arguments?.getString("title").toString()
            val id = navBackStack.arguments?.getString("id").toString()
            BusinessList(navController, context, id, category)
        }

        composable(
            Screen.PreviewScreen.route,

            ) { navBackStack ->

            PreviewScreen(navController, viewModel);
        }


    }
}

@Composable
fun HomeBottomViews(
    navController: NavHostController, padding: PaddingValues, mainNavControllers: NavHostController
) {
    val context = LocalContext.current
    var viewmodel = HomeViewModel()
    try {

        var act = context as MainActivity
        viewmodel = ViewModelProvider(act).get(HomeViewModel::class.java)

    } catch (_: Exception) {
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(padding),
        builder = {

            composable(Screen.Discover.route) {
                DicoverScreen(mainNavControllers, viewmodel)
            }
//            composable(Screen.Offers.route) {
//                HomeScreen(navController, "Offer", 2, mainNavControllers)
//            }

            composable(Screen.Offers.route) {
                OfferScreen(mainNavControllers)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController, "Home", 3, mainNavControllers, viewmodel)
            }
            composable(Screen.Explor.route) {
                ExploerScreen(navController, viewmodel)
            }
            composable(Screen.Videos.route) {
                VideoScreen(viewmodel)
            }

        })

}


val navDrawerSideMenu = listOf(
    SideMenuScreen.Profile,
    SideMenuScreen.MyBusiness,
    SideMenuScreen.Notifications,
    SideMenuScreen.Share,
    SideMenuScreen.HowIs,
    SideMenuScreen.Terms,
    SideMenuScreen.Logout,
)


val bottomNavItemsIcons = listOf(
    BottomNavItem(
        label = Screen.Discover.route, icon = R.drawable.discover,
//            route = Screen.Discover
        route = Screen.Discover.route
    ),
    BottomNavItem(
        label = Screen.Offers.route, icon = R.drawable.discount, route = Screen.Offers.route
    ),
    BottomNavItem(
        label = Screen.Home.route, icon = R.drawable.home, route = Screen.Home.route
    ),
    BottomNavItem(
        label = Screen.Explor.route, icon = R.drawable.explore, route = Screen.Explor.route
    ),
    BottomNavItem(
        label = Screen.Videos.route, icon = R.drawable.play, route = Screen.Videos.route
    ),
)

data class BottomNavItem(
    // Text below icon
    val label: String,
    // Icon
    val icon: Int,
    // Route to the specific screen
    val route: String,
)

