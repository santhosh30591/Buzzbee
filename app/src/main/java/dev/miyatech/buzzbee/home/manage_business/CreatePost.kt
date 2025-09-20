package dev.miyatech.buzzbee.home.manage_business

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.MainActivity
import dev.miyatech.buzzbee.Screen
import dev.miyatech.buzzbee.model.PostModel
import dev.miyatech.buzzbee.ui.alerts.ShowToast
import dev.miyatech.buzzbee.ui.alerts.createPostAlerts
import dev.miyatech.buzzbee.ui.theme.appThemeCardTint
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.BottomSheetStateList
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui_components.StringToBitMap
import dev.miyatech.buzzbee.ui_components.TextFieldWithImageSelect
import dev.miyatech.buzzbee.viewmodel.ManageByBusinessViewModel
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.Calendar


@SuppressLint("NewApi")
@Composable
fun CreatePost(
    navController: NavController, viewModel: ManageByBusinessViewModel = ManageByBusinessViewModel()
) {

    var showBottomView by remember { mutableStateOf(false) }
    var dig_title = ""
    val context = LocalContext.current

    val arrayList by remember {
        mutableStateOf(mutableStateListOf<PostModel>())
    }

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


    val selectDate by remember { mutableStateOf(Calendar.getInstance()) }


    var showToast by remember { mutableStateOf(false) }
    var toastMsg by remember { mutableStateOf("") }
    var showPostView by remember { mutableStateOf(false) }
    var updatePostView by remember { mutableStateOf(false) }
    var updatePostPosi by remember { mutableStateOf(0) }
    var fromDate by remember { mutableStateOf(viewModel.fromDate.value.toString()) }
    var toDate by remember { mutableStateOf(viewModel.toDate.value.toString()) }


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        HomeTitleBarBack(text = "Create Post", navController)
        Column(
            modifier = Modifier.weight(1f),
//                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val density = LocalDensity.current
            var height by remember {
                mutableStateOf(0.dp)
            }


            Column(
                modifier = Modifier
                    .background(appThemePrimary)
                    .background(Color.White)
//                    .verticalScroll(scroll)
                    .onSizeChanged {
                        height = with(density) {
                            it.height.toDp()
                        }
                    }

                    .padding(start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                TextFieldWithImageSelect(
                    value = viewModel.postTypes.value.toString(),
                    onChange = { data ->
                        viewModel.updateType(data.toString())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            dig_title = "Post Type"
                            showBottomView = true

                        },
                    label = "Post Type",
                    placeholder = "Select post type",
                    icon = Icons.Default.List
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    CardViewDate(
                        Icons.Default.CalendarMonth,
                        fromDate.toString(),
                        onclick = {
                            val calendar = Calendar.getInstance()
                            val year = calendar.get(Calendar.YEAR)
                            val month = calendar.get(Calendar.MONTH)
                            val day = calendar.get(Calendar.DAY_OF_MONTH)


                            val datePickerDialog = DatePickerDialog(
                                context, { _, selectedYear, selectedMonth, selectedDay ->
                                    fromDate =
                                        "$selectedDay/${selectedMonth + 1}/$selectedYear".toString()
                                    selectDate.set(selectedYear, selectedMonth, selectedDay)

                                    toDate = "To Date".toString()
                                    viewModel.updateFromDate(fromDate)
                                    viewModel.updateToDate("To Date")
                                }, year, month, day

                            )


                            datePickerDialog.datePicker.minDate = calendar.timeInMillis
                            datePickerDialog.show()
                        },
                        modifier1 = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))


                    ShowToast(
                        showToast = showToast,
                        outSide = {},
                        onDismiss = { showToast = false },
                        onDismissCloseIcons = { showToast = false },
                        title = "",
//                        subTitle = if (submitToast) "Are you sure you want to publish this post now?" else "Please select from date "


                        subTitle = toastMsg
                    )

                    if (showBottomView) {
                        BottomSheetStateList(dig_title,
                            list = viewModel.postType,
                            onDismiss = { showBottomView = false },
                            onItemSelected = { temp ->
                                showBottomView = false
                                viewModel.updateType(temp.name)
                            })
                    }
                    CardViewDate(
                        Icons.Default.CalendarMonth, toDate.toString(), onclick = {

                            if (fromDate.toString().equals("From Date")) {

                                toastMsg = "Please select from date"
                                showToast = true
                            } else {
                                val calendar = Calendar.getInstance()
                                val year = calendar.get(Calendar.YEAR)
                                val month = calendar.get(Calendar.MONTH)
                                val day = calendar.get(Calendar.DAY_OF_MONTH)


                                val datePickerDialog = DatePickerDialog(
                                    context, { _, selectedYear, selectedMonth, selectedDay ->
                                        toDate =
                                            "$selectedDay/${selectedMonth + 1}/$selectedYear"
                                        viewModel.updateToDate(toDate)
                                    }, year, month, day

                                )
                                datePickerDialog.datePicker.minDate = selectDate.timeInMillis
                                datePickerDialog.show()

                            }

                        }, modifier1 = Modifier.weight(1f)
                    )

                }

                if (showPostView) {
                    createPostAlerts(outSide = {}, submit = { model ->
                        viewModel.addPost1(model.title, model.description, model.bitmapString)
                        showPostView = false;
                    }, onDismissCloseIcons = {
                        showPostView = false
                    }, title = "Deal " + (arrayList.size + 1).toString()
                    )
                }

                if (updatePostView) {

                    createPostAlerts(
                        outSide = {},
                        submit = { model ->
                            model.isExpand = false
                            viewModel.updatePost(updatePostPosi, model)
                            updatePostView = false;
                        },
                        onDismissCloseIcons = {
                            updatePostView = false
                        },
                        title = "Deal " + (updatePostPosi + 1).toString(),
                        true,
                        arrayList[updatePostPosi]
                    )
                }


                Box(modifier = Modifier.fillMaxWidth(), Alignment.Center) {
                    Button(onClick = {

                        showPostView = true
//                        var img =
//                            "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"
//                        viewModel.addPost1(
//                            " test + 1", "some desc grdcf jk", img
//                        )

                    }) {
                        Text(text = " Add Post ")
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    PostList(arrayList = arrayList, update = { posi ->
                        updatePostView = true
                        updatePostPosi = posi

                    }, delete = { po ->
                        viewModel.removePost(arrayList[po])

                    }, viewAll = { po ->

                        var model = arrayList[po]
                        model.isExpand = !model.isExpand
                        viewModel.updatePost(po, model)

                    }, navController)
                }

            }
        }
        if (arrayList.isNotEmpty()) Row(

            Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround

        ) {
            Button(
                onClick = {

                    if (viewModel.postTypes.value.toString().equals("")) {
                        toastMsg = "Please select post type"
                        showToast = true
                    } else if (fromDate.toString().equals("From Date")) {
                        toastMsg = "Please select from date"
                        showToast = true
                    } else if (toDate.toString().equals("To Date")) {

                        toastMsg = "Please select to date"
                        showToast = true
                    } else {
                        viewModel.updateDate(fromDate.toString(), toDate.toString())
                        navController.navigate(Screen.PreviewScreen.route)

                    }

                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = appThemePrimary80)
            ) {


                Text(text = "Preview")
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = {
                    toastMsg = "Are you sure you want to publish this post now?"
                    showToast = true
                }, Modifier.weight(1f)

            ) {
                Text(text = "Submit")
            }
        }
    }
}


@Composable
fun PostList(
    arrayList: SnapshotStateList<PostModel>,
    update: (index: Int) -> Unit,
    delete: (index: Int) -> Unit,
    viewAll: (index: Int) -> Unit,
    navController: NavController
) {
    if (arrayList.size == 0) {
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            var corrnerPading = 5.dp
            var startEndPading = 10.dp
            items(arrayList.size) { item ->


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(corrnerPading))
                        .background(appThemePrimary)
                        .border(
                            width = 1.dp,
                            color = appThemePrimary,
                            shape = RoundedCornerShape(corrnerPading)
                        )
                        .clickable {
                            viewAll(item)
                        }

                        .wrapContentHeight(),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(startEndPading),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Text(
                            text = arrayList[item].title,
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp
                        )

                        Image(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .padding(3.dp)
                                .size(20.dp)
                                .clickable {
                                    update(item)
                                },

                            )

                        Image(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .padding(3.dp)
                                .size(20.dp)
                                .clickable {
                                    delete(item)
                                },

                            )


                    }


                    Text(
                        text = "", modifier = Modifier

                            .fillMaxWidth()
                            .height(1.dp)
                            .background(appThemeCardTint)
                    )

                    Text(
                        text = arrayList[item].description,
                        color = Color.Gray,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(startEndPading)
                    )


                    AnimatedVisibility(visible = arrayList[item].isExpand) {
                        Image(
//                            bitmap = fileToBitMaps(arrayList[item].file).asImageBitmap(),
                            bitmap = StringToBitMap(arrayList[item].bitmapString)!!.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .background(Color.White)
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
                                .background(appThemeTintGray))
                    }
                }
            }

        }
    }
}


@Composable
fun CardViewDate(
    imageVector: ImageVector, title: String, onclick: () -> Unit, modifier1: Modifier
) {

    Box(
        modifier = modifier1 // Takes 1/3 of the available width
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = appThemeCardTint, shape = RoundedCornerShape(5.dp)
            )
            .padding(14.dp)
            .clickable { onclick() }
            .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = title,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            Image(
                imageVector = imageVector,
                contentDescription = title,
                colorFilter = ColorFilter.tint(appThemePrimary),
                modifier = Modifier.size(24.dp)
            )
        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreatePost() {
    CreatePost(navController = rememberNavController())
}