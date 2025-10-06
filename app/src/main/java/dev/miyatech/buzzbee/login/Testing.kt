package dev.miyatech.buzzbee.login


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.ui.theme.BuzzbeeTheme
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.viewmodel.LearningViewModel
import kotlinx.coroutines.delay

@Composable
fun Temp(navController: NavController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    var viewModel by remember { mutableStateOf(LearningViewModel()) }

    val context = LocalContext.current


    var liveCount by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {

        viewModel = ViewModelProvider(context as MainActivity).get(LearningViewModel::class.java)

        viewModel.mubookings.observe(context as MainActivity, { i ->
//            liveCount = i

            liveCount = i.message
        })

        // AnimationEffect

        scale.animateTo(
            targetValue = 0.7f, animationSpec = tween(durationMillis = 800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(50L)

    }


    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = "Logo",
//            modifier = Modifier.scale(scale.value)
//        )

        Column {
            Icon(

                Icons.Default.Person,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .scale(scale.value)
                    .clickable {


                        print(" temp kjdfbd ")

                        viewModel.getBooking()

//                        print(" temp " + viewModel.count)
//                        modelViewCall(viewModel)
                    },
                tint = appThemePrimary,
                contentDescription = "",
            )

            Text("T " + liveCount)
        }


    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Temp() {
    val navController = rememberNavController()
    BuzzbeeTheme() {
        SplashScree(navController)
    }
}