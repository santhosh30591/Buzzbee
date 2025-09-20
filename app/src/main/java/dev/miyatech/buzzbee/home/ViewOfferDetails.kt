package dev.miyatech.buzzbee.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack


@Composable
fun ViewMoreOffer(navController: NavController) {


    val context = LocalContext.current


    Surface {


        Column(
            modifier = Modifier

                .fillMaxSize()
                .background(Color.White),


            ) {

            HomeTitleBarBack(text = " View Details ", navController)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                Alignment.Center
            ) {
                LazyColumn(


                ) {

                    items(10) { it ->


                        Icon(
                            imageVector = Icons.Default.LocalOffer,
                            contentDescription = "",
                            Modifier
                                .size(50.dp)
                                .padding(10.dp)
                        )
                        
                    }


                }
            }


        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViewMoreOffer() {
    ViewMoreOffer(navController = rememberNavController())

}