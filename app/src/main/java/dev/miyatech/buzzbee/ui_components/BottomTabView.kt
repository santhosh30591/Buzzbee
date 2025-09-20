package dev.miyatech.buzzbee.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary


@Composable
fun HomeTitleBar(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(appThemePrimary),
    ) {
        Text(
            text = text,
            color = Color.White,

            modifier = Modifier.padding(all = 17.dp),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun HomeTitleBarBack(
    text: String, navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(appThemePrimary),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier
                .padding(start = 20.dp)
                .size(31.dp)
                .clickable {

                    navController.popBackStack()
//                    coroutineScope.launch {
//                        drawerState.apply {
//                            if (isClosed) open() else close()
//                        }
//                    }
                },
        )

        Text(
            text = text,
            color = Color.White,

            modifier = Modifier.padding(all = 17.dp),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun profilesScreenHidder() {

    HomeTitleBarBack(" Profile ", rememberNavController())
//    BottomTabLineView(1)

}

@Composable
fun BottomTabLineView(
    posi: Int

) {


    var paddings = 10

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(appThemePrimary),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {


        if (posi == 1) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = (paddings - 3).dp, end = (paddings + 3).dp)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .background(appThemeAccident)
            ) {

            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
        } else if (posi == 2) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .background(appThemeAccident)
            ) {

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
        }
        if (posi == 3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .background(appThemeAccident)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
        } else if (posi == 4) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .background(appThemeAccident)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
        }
        if (posi == 5) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = paddings.dp, end = paddings.dp)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .background(appThemePrimary)
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = (paddings + 5).dp, end = (paddings - 5).dp)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .background(appThemeAccident)
            ) {

            }
        }

    }
}
