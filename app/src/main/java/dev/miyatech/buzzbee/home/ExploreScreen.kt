package dev.miyatech.buzzbee.home


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.model.DiscoverResult
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.theme.BuzzbeeTheme
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.BottomTabLineView
import dev.miyatech.buzzbee.ui_components.HomeTitleBar
import dev.miyatech.buzzbee.viewmodel.HomeViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ExploerScreen(navController: NavController, viewModel: HomeViewModel) {

    var discoverList by remember { mutableStateOf(ArrayList<DiscoverResult>()) }
    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("") }
    val context = LocalContext.current

//    discoverList.add(DiscoverResult("", "name", "sample"))


    LaunchedEffect(key1 = true) {

        try {
            context as MainActivity

            viewModel.discountState.observe(context) { response ->

                when (response) {
                    is NetworkResult.Loading -> {
                        isLoading = true
                    }

                    is NetworkResult.Success -> {
                        isLoading = false

                        if (response.data.size != 0) {
                            discoverList = response.data
                        }
                    }

                    is NetworkResult.Error -> {
                        showToast = true
                        isLoading = false
                        errorMsg = response.message
                    }

                    else -> {
                        isLoading = false
                    }
                }
            }
        } catch (_: Exception) {

        }
        viewModel.discoverLoading(context)
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

                HomeTitleBar(text = "Explore")

                if (discoverList.isNotEmpty()) {
                    loadExploreUi(discoverList, context)
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(Color.White)
                            .clickable { viewModel.discoverLoading(context) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Explore  size ${discoverList.size} ")
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BottomTabLineView(posi = 4)
            }


        }
    }
}

@Composable
fun loadExploreUi(discoverList: ArrayList<DiscoverResult>, context: Context) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        items(discoverList.size) {

                position ->


            var isExpand by remember {
                mutableStateOf(false)
            };
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0x41F8F7F7))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = appThemeTintGray.copy(alpha = .5f),

                        )

                    .clickable {

                        isExpand = !isExpand

                    }


            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(10.dp)
                ) {

                    //Profile image
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .size(
                                screenWidth / 9F, screenWidth / 9F
                            )
                            .background(
                                color = Color.Black.copy(alpha = 0.1F), shape = CircleShape
                            )
                    ) {
                        AsyncImage(
                            model = "" + discoverList[position].imageUrl, // Replace with your image URL
                            contentDescription = "A description of the image for accessibility",
                            // Optional: Add placeholder and error drawables
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            error = painterResource(id = R.drawable.error_image),
                            // Optional: Customize contentScale, transformations, etc.
                            contentScale = ContentScale.Crop
                        )
                    }



                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {


                        Text(
                            text = discoverList[position].category, style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = if (isSystemInDarkTheme()) {
                                    Color.White
                                } else {
                                    Color.Black
                                },
                            ), maxLines = 1
                        )

                        Text(
                            text = "Last message to go here " + discoverList[position].id,
                            style = TextStyle(
                                fontSize = 14.sp,
                                textAlign = TextAlign.Start,
                                color = Color.Gray,
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )


                    }


                }
                AnimatedVisibility(visible = isExpand) {


                    Spacer(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(appThemeTintGray)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = "Open", modifier = Modifier
                                .padding(all = 5.dp)
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Share", modifier = Modifier
                                .padding(all = 5.dp)
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "View More", modifier = Modifier
                                .padding(all = 5.dp)
                                .weight(1f),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploerScreens() {
    BuzzbeeTheme {
        val context = LocalContext.current


        ExploerScreen(rememberNavController(), HomeViewModel())

//        ExploerScreen(getTemp(), LocalContext.current)
    }
}