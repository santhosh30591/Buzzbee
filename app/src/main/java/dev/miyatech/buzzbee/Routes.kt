package dev.miyatech.buzzbee

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VideoSettings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("Login")
    object Register : Screen("Register")
    object HomeMain : Screen("main_home")
    object Home : Screen("Home")
    object Profile : Screen("Profile")
    object Notification : Screen("Notifications")
    object ManageBusiness : Screen("ManageBusiness")
    object CreatePost : Screen("Create Post")
    object Support : Screen("Support")
    object Discover : Screen("Discover")
    object SubCategory : Screen("SubCategory")
    object Offers : Screen("Offers")
    object Explor : Screen("Explore")
    object Videos : Screen("Videos")
    object BusinessList : Screen("BusinessList")
    object BusinessDetails : Screen("BusinessDetails")
    object Terms : Screen("Terms")
    object Share : Screen("Share")
    object HowIsWork : Screen("How is Work")

    object MyBusiness : Screen("My Business")
    object Location1 : Screen("locations1")
    object VCard : Screen("MyBusiness")
    object ZoomingImg : Screen("ZoomingImage")
    object PreviewScreen : Screen("PreviewScreen")
}


sealed class SideMenuScreen(
    val icon: ImageVector,
    val contentDescription: String,
    val title: String
) {
    object Profile : SideMenuScreen(
        icon = Icons.Default.Person2,
        contentDescription = "Go to home screen",
        title = Screen.Profile.route
    )

    object MyBusiness : SideMenuScreen(
        icon = Icons.Default.Business,
        contentDescription = "Go to about screen",
        title = Screen.MyBusiness.route
    )

    object Notifications : SideMenuScreen(
        icon = Icons.Default.Notifications,
        contentDescription = "Go to about screen",
        title = Screen.Notification.route
    )

    object Share : SideMenuScreen(
        icon = Icons.Default.Share,
        contentDescription = "Go to about screen",
        title = Screen.Share.route
    )

    object HowIs : SideMenuScreen(
        icon = Icons.Default.VideoSettings,
        contentDescription = "Go to about screen",
        title = Screen.HowIsWork.route
    )

    object Terms : SideMenuScreen(
        icon = Icons.Default.DocumentScanner,
        contentDescription = "Go to about screen",
        title = Screen.Terms.route
    )

    object Logout : SideMenuScreen(
        icon = Icons.Default.Logout,
        contentDescription = "Go to about screen",
        title = "Logout"
    )
}