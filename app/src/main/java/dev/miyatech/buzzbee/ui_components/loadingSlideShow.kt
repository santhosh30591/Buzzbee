package dev.miyatech.buzzbee.ui_components


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.model.DiscoverResult
import dev.miyatech.buzzbee.ui.theme.appThemeAccident50
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun loadingSlideShow(sliderList: ArrayList<DiscoverResult>) {

    var pageKey by remember { mutableStateOf(0) }

    val pagerState = rememberPagerState(sliderList.size)


    val itemSize = 50.dp
    val density = LocalDensity.current
    val itemSizePx = with(density) { itemSize.toPx() }
    val itemsScrollCount = 150


    LaunchedEffect(pageKey) {


        while (true) {

            val nextPage = (pagerState.currentPage + 1) % sliderList.size

            coroutineScope {
                delay(2000)
                if (0 <= pagerState.currentPage || pagerState.currentPage < sliderList.size) {
                    pagerState.animateScrollToPage(nextPage)
                    pageKey++
                } else {
                    pageKey++
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()

            .height(250.dp)
    ) {
        HorizontalPager(
            pageCount = sliderList.size,
            state = pagerState,
//            contentPadding = PaddingValues(horizontal = 10.dp),
//            pageSpacing = 10.dp
        ) { currentPage ->


            Box(
                modifier = Modifier
                    .fillMaxSize(), Alignment.BottomCenter
            ) {

                AsyncImage(
                    model = sliderList[currentPage].imageUrl, // Replace with your image URL
//                        model = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", // Replace with your image URL
                    contentDescription = "",
                    // Optional: Add placeholder and error drawables
                    placeholder = painterResource(id = R.drawable.placeholder_image),
                    error = painterResource(id = R.drawable.error_image),
                    modifier = Modifier
                        .fillMaxSize()
//                        .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                    , contentScale = ContentScale.Crop
                    // Optional: Customize contentScale, transformations, etc.

                )

                Box(
                    modifier = Modifier.padding(bottom = 10.dp)


                ) {
                    PageIndicator(
                        pageCount = sliderList.size,
                        currentPage = pagerState.currentPage,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}


@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}


@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) appThemeAccident50 else appThemePrimary)
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreviews() {

    val context = LocalContext.current
    var discoverList by remember { mutableStateOf(ArrayList<DiscoverResult>()) }

    discoverList.add(
        DiscoverResult(
            "1", "sample", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
        )
    )
    discoverList.add(
        DiscoverResult(
            "1", "sample", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
        )
    )
    discoverList.add(
        DiscoverResult(
            "1", "sample", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"
        )
    )
    ImageSlider(discoverList)
}

@Composable
fun ImageSlider(images: ArrayList<DiscoverResult>) {
    var currentImageIndex by remember { mutableStateOf(0) }
    var isAnimating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {
        // Scrollable Row of Cards
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(images) { index, image ->
                Card(
                    modifier = Modifier
                        .width(140.dp)
                        .height(120.dp)
                        .bounceClick()
                        .clickable {
                            if (index != currentImageIndex && !isAnimating) {
                                isAnimating = true
                                coroutineScope.launch {
                                    val delayMillis = 1000L
                                    // Wait for the card to change color before animating
                                    delay(delayMillis / 2)
                                    currentImageIndex = index
                                    delay(delayMillis)
                                    isAnimating = false
                                }
                            }
                        },
//                        elevation = 4.dp
                ) {
                    AsyncImage(
                        model = images[index].imageUrl, // Replace with your image URL
//                        model = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg", // Replace with your image URL
                        contentDescription = "",
                        // Optional: Add placeholder and error drawables
                        placeholder = painterResource(id = R.drawable.placeholder_image),
                        error = painterResource(id = R.drawable.error_image),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                        // Optional: Customize contentScale, transformations, etc.

                    )

                }
            }

        }


    }
}