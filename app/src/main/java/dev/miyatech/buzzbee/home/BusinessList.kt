package dev.miyatech.buzzbee.home


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.R
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.model.BusinessResult
import dev.miyatech.buzzbee.netwoork.NetworkResult
import dev.miyatech.buzzbee.ui.alerts.ShowLoading
import dev.miyatech.buzzbee.ui.theme.BuzzbeeTheme
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui_components.bounceClick
import dev.miyatech.buzzbee.viewmodel.HomeViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun BusinessList(
    navController: NavController,
    context: Context,
    id: String = "",
    title: String = ""
) {

    var viewModel = HomeViewModel()
    try {
        viewModel = ViewModelProvider(context as MainActivity).get(HomeViewModel::class.java)
    } catch (e: Exception) {

    }


    var discoverList by remember { mutableStateOf(ArrayList<BusinessResult>()) }
    var isLoading by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf("No Data Found") }


    LaunchedEffect(key1 = true) {
        viewModel.businessListApi(context, "" + id)
        try {
            viewModel.bisinessList.observe(context as MainActivity) { response ->

                when (response) {
                    is NetworkResult.Loading -> {
                        isLoading = true
                    }

                    is NetworkResult.Success -> {
                        isLoading = false

                        val jsonString = Gson().toJson(response)

                        if (response.data.errorCode == 0) {
                            discoverList = response.data.result
                        } else {
                            errorMsg = response.data.message
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


                HomeTitleBarBack(text = title, navController)


                if (discoverList.isNotEmpty()) {
                    businessListLoading(discoverList, context, navController)
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
                        Text(text = errorMsg)
                    }
                    ShowLoading(isLoading = isLoading, submit = {

                    })

                }


            }


        }
    }
}


@Composable
fun businessListLoading(
    discoverList: ArrayList<BusinessResult>,
    context: Context,
    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {

        items(discoverList.size) { position ->

            Row(
//                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .padding(5.dp)
                    .clickable {

                        var title = discoverList[position].names
                        var id = discoverList[position].id
                        navController.navigate(Screen.BusinessDetails.route + "/$title/$id")


                    }
            ) {

                //Profile image


                Column(
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier
                        .fillMaxWidth(),

                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {


                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .size(
                                screenWidth / 8F, screenWidth / 8F
                            )
                            .padding(all = 4.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.1F), shape = CircleShape
                            )
                    ) {
                        AsyncImage(
                            model = "" + discoverList[position].img, // Replace with your image URL
                            contentDescription = "",
                            // Optional: Add placeholder and error drawables
                            placeholder = painterResource(id = R.drawable.placeholder_image),
                            error = painterResource(id = R.drawable.error_image),
                            // Optional: Customize contentScale, transformations, etc.
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = discoverList[position].names,
                        style = TextStyle(
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessListpri() {
    BuzzbeeTheme {
        val context = LocalContext.current
//        DicoverScreen(rememberNavController(), HomeViewModel())

        BusinessList(rememberNavController(), context, "1", "Sample title")
    }
}