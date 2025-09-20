package dev.miyatech.buzzbee.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.model.PostModel
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui_components.StringToBitMap
import dev.miyatech.buzzbee.viewmodel.ManageByBusinessViewModel
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


@Composable
fun PreviewScreen(
    navController: NavController = rememberNavController(),
    viewModel: ManageByBusinessViewModel = ManageByBusinessViewModel()
) {

    val context = LocalContext.current

    val arrayList by remember {
        mutableStateOf(mutableStateListOf<PostModel>())
    }

    Surface {

        LaunchedEffect(key1 = null) {
            try {
                viewModel.getPostType()
                viewModel.postList.observe(context as MainActivity) {
                    arrayList.clear()
                    arrayList.addAll(it)
                }
            } catch (e: Exception) {
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White),
        ) {

            HomeTitleBarBack(text = "Post Details", navController)


            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenWidth / 2F))


            ) {

                var images = ""
                try {
                    images =
                        "https://s3.amazonaws.com/images.seroundtable.com/google-amp-1454071566.jpg"
                } catch (e: Exception) {

                }
                AsyncImage(
                    model = images, // Replace with your image URL
                    contentDescription = "",
                    // Optional: Add placeholder and error drawables
                    placeholder = painterResource(id = R.drawable.placeholder_image),
                    error = painterResource(id = R.drawable.error_image),
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                    // Optional: Customize contentScale, transformations, etc.

                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                var startEndPading = 10.dp
                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appThemePrimary80)

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(startEndPading),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(
                            "MSR Engineering",
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp
                        )


                    }
                }



                Column(
                    Modifier
                        .fillMaxWidth()
//                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .padding(10.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Date", fontSize = 14.sp)
                        Text(
                            text = viewModel.createPostDate.value.toString(),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Direction", fontSize = 14.sp)
                        Text(
                            text = "13 Km", fontSize = 14.sp, color = Color.Black
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Mobile ", fontSize = 14.sp)
                        Text(
                            text = "7639326563", fontSize = 14.sp, color = Color.Black
                        )
                    }

                }

                BoxWithConstraints(
                    Modifier.weight(1f)
                ) {
                    PostPreivewList(arrayList, navController)
                }


            }

        }
    }
}


@Composable
fun PostPreivewList(
    arrayList: SnapshotStateList<PostModel>, navController: NavController
) {
    if (arrayList.size == 0) {
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(all = 10.dp)
        ) {

            var corrnerPading = 5.dp

            items(arrayList.size) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(corrnerPading))
//                        .padding(start = 10.dp, end = 10.dp)
                        .border(
                            width = 1.dp,
                            color = appThemeTintGray,
                            shape = RoundedCornerShape(corrnerPading)
                        )
                        .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
//                            bitmap = fileToBitMaps(arrayList[item].file).asImageBitmap(),
                        bitmap = StringToBitMap(arrayList[item].bitmapString)!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,

                        modifier = Modifier
                            .width(60.dp)

                            .height(60.dp)
                            .background(Color.White)
                            .padding(8.dp)
                            .clickable {


                                var uriString = ""
                                var org = arrayList[item].bitmapString.toString()
                                try {
                                    uriString = URLEncoder.encode(org, "UTF-8")
                                    println("Encoded string (UTF-8, String enc): $uriString")
                                    navController.navigate(Screen.ZoomingImg.route + "/$uriString")
                                } catch (e: UnsupportedEncodingException) {
                                    System.err.println("Encoding not supported: " + e.message)
                                }


                            }
                            .clip(RoundedCornerShape(8.dp))
                            .background(appThemeTintGray))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(corrnerPading))

//                            .border(
//                                width = 1.dp,
//                                color = appThemeTintGray,
//                                shape = RoundedCornerShape(corrnerPading)
//                            )
                            .wrapContentHeight()
                            .padding(5.dp),

                        ) {


                        Text(
                            text = arrayList[item].title,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 15.sp
                        )






                        Text(
                            text = arrayList[item].description,
                            color = Color.Gray,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)

                        )


                    }
                }


            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScreens() {


    PreviewScreen()

}