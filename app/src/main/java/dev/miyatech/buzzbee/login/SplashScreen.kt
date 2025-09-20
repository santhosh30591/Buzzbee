package dev.miyatech.buzzbee.login


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.ui.theme.BuzzbeeTheme
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui_components.DBHelper
import kotlinx.coroutines.delay

@Composable
fun SplashScree(navController: NavController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    val context = LocalContext.current
    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f, animationSpec = tween(durationMillis = 800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(50L)


        if (DBHelper(context as MainActivity).isLogin()) {
            navController.navigate(Screen.HomeMain.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true // This removes the splash screen from the back stack
                }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true // This removes the splash screen from the back stack
                }
            }
        }
    }


    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = "Logo",
//            modifier = Modifier.scale(scale.value)
//        )

        Icon(

            Icons.Default.Person,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .scale(scale.value),
            tint = appThemePrimary,
            contentDescription = "",
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreviewSplash() {
    val navController = rememberNavController()
    BuzzbeeTheme() {
        SplashScree(navController)
    }
}