package dev.miyatech.buzzbee.home.manage_business

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.model.PostModel
import dev.miyatech.buzzbee.ui.theme.appThemeCardTint
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.viewmodel.ManageByBusinessViewModel


@Composable
fun SupportCustomer(navController: NavController) {
    val scroll = rememberScrollState()
    var postType by remember { mutableStateOf("") }

    var showBottomView by remember { mutableStateOf(false) }
    var counts by remember { mutableStateOf(0) }


    var context = LocalContext.current

    var viewModel = ManageByBusinessViewModel()
    try {
        viewModel =
            ViewModelProvider(context as MainActivity).get(ManageByBusinessViewModel::class.java)

    } catch (_: Exception) {

    }


    var arrayList by remember {
        mutableStateOf(mutableStateListOf<PostModel>())
    }


    LaunchedEffect(key1 = null) {

        viewModel =
            ViewModelProvider(context as MainActivity).get(ManageByBusinessViewModel::class.java)

        viewModel.postList.observe(context as MainActivity) {
            counts = it.size
            arrayList.clear()
            arrayList.addAll(it)
        }
    }




    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        HomeTitleBarBack(text = "Customer Support", navController)
        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Column(
                modifier = Modifier
                    .background(appThemePrimary)
                    .background(Color.White)

//                    .onSizeChanged {
//                        height = with(density) {
//                            it.height.toDp()
//                        }
//                    }

                    .padding(start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Spacer(modifier = Modifier.height(15.dp))


                Box(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                    Button(onClick = {
//                        viewModel.addPost(" counts $counts ", "")

                    }) {
                        Text(text = "Add Post $counts and " + arrayList.size)
                    }
                }


                CustomerList(arrayList, viewModel)
            }
        }
    }
}


@Composable
fun CustomerList(arrayList: SnapshotStateList<PostModel>, viewModel: ManageByBusinessViewModel) {
    if (arrayList.size == 0) {
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),

            ) {
            items(arrayList.size) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp, color = appThemeCardTint, shape = RoundedCornerShape(5.dp)
                        )
                        .padding(5.dp)
                        .wrapContentHeight(),

                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(
                            text = arrayList[item].title,
                            color = Color.Black,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp
                        )

                        Image(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(appThemePrimary),
                            modifier = Modifier
                                .padding(3.dp)
                                .size(20.dp)
                                .clickable { },

                            )

                        Image(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(appThemePrimary),
                            modifier = Modifier
                                .padding(3.dp)
                                .size(20.dp)
                                .clickable {},

                            )


                    }


                    Text(
                        text = "",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .fillMaxWidth()

                            .height(1.dp)

                            .background(appThemeCardTint)
                    )

                    Text(
                        text = arrayList[item].description, color = Color.Black, fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                }


            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SupportCustomer() {
    SupportCustomer(navController = rememberNavController())
}