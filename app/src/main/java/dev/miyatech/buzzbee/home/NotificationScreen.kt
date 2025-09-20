package dev.miyatech.buzzbee.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui.alerts.shimmerLoadingAnimation
import kotlinx.coroutines.delay


@Composable
fun NotificationScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            HomeTitleBarBack(text = " Notifications ", navController)


            if (!isLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            isLoading = !isLoading
                        }, // Make the Box fill the available space
                    contentAlignment = Alignment.Center // Center its content both horizontally and vertically
                ) {


                    Card() {
                        Column(
                            modifier = Modifier.padding(all = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var expended by remember() {
                                mutableStateOf(true)
                            }
                            Image(
                                painterResource(id = R.drawable.error_image),
                                contentDescription = "",
                                modifier = Modifier.clickable { expended = !expended }
                            )
                            AnimatedVisibility(visible = expended) {
                                Text(
                                    text = "No Data Found.",
                                    fontSize = 18.sp,
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge

                                )

                            }


                        }
                    }

                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(6) { item ->


                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerLoadingAnimation(isLoading)
                                .padding(all = 5.dp)

                                .border(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.LightGray,
                                    width = 1.dp
                                )

                        ) {

                            Row(
                                Modifier.padding(all = 10.dp),
                                verticalAlignment = Alignment.CenterVertically

                            ) {

                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(color = Color.LightGray)
                                        .size(height = 40.dp, width = 40.dp)
                                        .shimmerLoadingAnimation(
                                            isLoading
                                        )
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Column() {
                                    Box(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(4.dp))
                                            .background(color = Color.LightGray)
                                            .height(height = 25.dp)
                                            .fillMaxWidth()
                                            .shimmerLoadingAnimation(
                                                isLoading
                                            )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(4.dp))
                                            .background(color = Color.LightGray)
                                            .size(200.dp, 15.dp)
                                            .shimmerLoadingAnimation(
                                                isLoading
                                            )
                                    )
                                }


                            }

                        }


                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = isLoading) {
        if (isLoading) {
            delay(5000)
            isLoading = !isLoading
        }

    }

}


@Composable
fun ContentLoadingButton(
    modifier: Modifier,
    onClick: () -> Unit,

    isLoading: Boolean,
) {

    Button(
        modifier = modifier, onClick = {
            onClick()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = if (isLoading) {

                Color.Red
            } else {
                Color.Green

            }
        )
    ) {
        Text(
            "Loading $isLoading", color = if (isLoading) Color.White else {
                Color.Black
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreen() {
    NotificationScreen(navController = rememberNavController())

}