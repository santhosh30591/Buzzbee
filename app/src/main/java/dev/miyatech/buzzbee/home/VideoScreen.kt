package dev.miyatech.buzzbee.home


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.model.VideoListModel
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.alerts.ShowToast
import dev.miyatech.buzzbee.ui.alerts.shimmerLoadingAnimation
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.BottomTabLineView
import dev.miyatech.buzzbee.ui_components.HomeTitleBar
import dev.miyatech.buzzbee.ui_components.bounceClick
import dev.miyatech.buzzbee.ui_components.shareString
import dev.miyatech.buzzbee.viewmodel.HomeViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun VideoScreen(model: HomeViewModel) {

    val context = LocalContext.current
    val viewModel by remember { mutableStateOf(model) }
    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }
    var videoLists by remember { mutableStateOf(viewModel.videoList) }

    LaunchedEffect(key1 = true) {
        try {
            val act = context as MainActivity
            viewModel.video.observe(act) { resource ->

                when (resource) {
                    is NetworkResult.Loading -> {
                        isLoading = true
                    }

                    is NetworkResult.Success -> {
                        isLoading = false
                        if (resource.data.errorCode == 0) {
                            videoLists = resource.data.results
                        } else {
                            showToast = true
                            errorMsg = resource.data.message + " " + resource.data.message
                        }
                    }

                    is NetworkResult.Error -> {
                        showToast = true
                        isLoading = false
                        errorMsg = resource.message
                    }

                    else -> {
                        isLoading = false
                    }
                }


            }
        } catch (_: Exception) {
        }

        viewModel.loadingVideos(context)
    }

    Surface {


        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.White),

                ) {

                if (showToast) {
                    ShowToast(
                        outSide = {},
                        onDismiss = { showToast = false },
                        onDismissCloseIcons = { showToast = false },
                        title = "",
                        subTitle = "" + errorMsg
                    )
                }
                HomeTitleBar(text = "Videos")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .clickable { viewModel.loadingVideos(context) },

                    ) {


                    if (isLoading && videoLists.size == 0) {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(6) {


                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .shimmerLoadingAnimation(isLoading)
                                        .padding(all = 5.dp)
                                        .border(
                                            shape = RoundedCornerShape(15.dp),
                                            color = Color.LightGray,
                                            width = 1.dp
                                        )

                                ) {


                                    BoxWithConstraints(
                                        Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(color = Color.LightGray)
                                            .fillMaxWidth()
                                            .height(160.dp),

                                        ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .shimmerLoadingAnimation(isLoading)
                                                .padding(all = 5.dp)
                                        ) {

                                        }
                                    }

                                    Spacer(modifier = Modifier.height(3.dp))
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

                                        Column {
                                            Box(
                                                modifier = Modifier
                                                    .clip(
                                                        shape = RoundedCornerShape(
                                                            4.dp
                                                        )
                                                    )
                                                    .background(color = Color.LightGray)
                                                    .height(height = 15.dp)
                                                    .fillMaxWidth()
                                                    .shimmerLoadingAnimation(
                                                        isLoading
                                                    )
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(
                                                        shape = RoundedCornerShape(
                                                            4.dp
                                                        )
                                                    )
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
                    } else {
                        if (videoLists.size == 0) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .clickable { viewModel.loadingVideos(context) },


                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Videos ")
                            }

                        } else {
                            ChatView(videoLists, context)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BottomTabLineView(posi = 5)
            }


        }
    }
}


@Composable
fun ChatView(list: ArrayList<VideoListModel>, context: Context) {


    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        items(list.size) { video ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = appThemeTintGray.copy(alpha = .5f),

                        )
                    .bounceClick()

                    .clickable {
//delay(300)
                        val youtubeUrl = list[video].url
                        // Or use "youtube://watch?v=yourVideoId"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                        context.startActivity(intent)

                    }) {
                Box(
                    modifier = Modifier.size(
                        screenWidth, screenWidth / 2.5F
                    )
                ) {


                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxHeight()
                    ) {

                        AsyncImage(
                            model = "" + list[video].thumb_image, // Replace with your image URL
                            contentDescription = "A description of the image for accessibility",
                            // Optional: Add placeholder and error drawables
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            error = painterResource(id = R.drawable.error_image),
                            // Optional: Customize contentScale, transformations, etc.
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                                .background(appThemeTintGray),

                            )


                        Image(
                            painter = painterResource(id = R.drawable.video_play),
                            contentDescription = "",

                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.White),

                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)),
                        )

                    }

                    Box(
                        contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()
                    ) {


                        Image(
                            imageVector = Icons.Default.Share,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .padding(all = 10.dp)
                                .size(23.dp)
                                .clickable {

                                    shareString(context, "" + list[video].share)
                                },
                        )
                    }
                }


                Row(

                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)


                ) {

                    //Profile image
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .size(
                                screenWidth / 11F, screenWidth / 11F
                            )
                            .background(
                                color = Color.Black.copy(alpha = 0.1F), shape = CircleShape
                            )
                    ) {
                        AsyncImage(
                            model = "" + list[video].logo, // Replace with your image URL
                            contentDescription = "A description of the image for accessibility",
                            // Optional: Add placeholder and error drawables
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            error = painterResource(id = R.drawable.error_image),
                            // Optional: Customize contentScale, transformations, etc.
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(.5.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(8.dp)
                    ) {


                        Text(
                            text = list[video].title, style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start,
                                color = Color.Black
                            ), maxLines = 2
                        )

                        Text(
                            text = list[video].address + " ." + list[video].last_update,
                            style = TextStyle(
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                color = Color.Gray,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VideoScreens() {


    val context = LocalContext.current
//    VideoScreen(rememberNavController())
    ChatView(getTemp(), context)
}


fun getTemp(): ArrayList<VideoListModel> {

    val list = ArrayList<VideoListModel>()

    list.add(
        VideoListModel(
            "1",
            "Sample message",
            "https://billpaynxt.in/portal/operator_icons/1723547679Untitled-3.png",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    )
    list.add(
        VideoListModel(
            "1",
            "Sample message",
            "https://billpaynxt.in/portal/operator_icons/1723547679Untitled-3.png"
        )
    )
    list.add(
        VideoListModel(
            "1",
            "Sample message",
            "https://billpaynxt.in/portal/operator_icons/1723547679Untitled-3.png"
        )
    )

    return list
}