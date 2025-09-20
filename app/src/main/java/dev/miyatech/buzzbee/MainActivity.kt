package dev.miyatech.buzzbee

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import dev.miyatech.buzzbee.ui.theme.BuzzbeeTheme
import dev.miyatech.buzzbee.ui.theme.appTheme
import dev.miyatech.buzzbee.ui.theme.appThemePrimary

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MaterialTheme(colorScheme = appTheme) {
                val view = LocalView.current
                val window = (view.context as Activity).window
                window.statusBarColor = appThemePrimary.toArgb()
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }

    }
}


@Composable
fun Greeting() {


        AppNavHost()

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BuzzbeeTheme {

//        HomeScreen(rememberNavController())
    }
}