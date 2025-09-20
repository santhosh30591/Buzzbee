package dev.miyatech.buzzbee.home


import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack


@Composable
fun WebViewsScreen(navController: NavController, url: String, title: String) {


    println("url $url and title $title")
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeTitleBarBack(text = title, navController)
        LazyColumn {
            item {
                AndroidView(
                    modifier = Modifier.wrapContentSize(),
                    factory = { context ->
                        WebView(context).apply {
                            loadUrl(url)
                        }
                    },
                )

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WebViewsScreen() {


    WebViewsScreen(navController = rememberNavController(), "", "")

}