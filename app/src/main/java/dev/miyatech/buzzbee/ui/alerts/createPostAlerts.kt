package dev.miyatech.buzzbee.ui.alerts


import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dev.miyatech.buzzbee.model.PostModel
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import dev.miyatech.buzzbee.ui_components.BitMapToString
import dev.miyatech.buzzbee.ui_components.StringToBitMap
import dev.miyatech.buzzbee.ui_components.TextFieldOutLine
import dev.miyatech.buzzbee.ui_components.uriToBitMap
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun createPostAlerts(
    outSide: () -> Unit,
    submit: (post: PostModel) -> Unit,
    onDismissCloseIcons: () -> Unit,
    title: String,
    isUpdate: Boolean = false,
    post: PostModel = PostModel()

) {
    var header by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    var headerError by remember {
        mutableStateOf(false)
    }
    var descriptionError by remember {
        mutableStateOf(false)
    }

    var imageError by remember {
        mutableStateOf(false)
    }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    var showImageSelect by remember { mutableStateOf(false) }

    var bitmapToString = ""

    if (isUpdate) {
        header = post.title
        description = post.description
        bitmap = StringToBitMap(post.bitmapString)
        bitmapToString = post.bitmapString
    }


    val redirectAppSetting = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { it ->
            Log.i(
                "ammu", "TEST 2:  " + it.resultCode + " and  " + it.data.toString()
            )
        })


    val imageScope = rememberCoroutineScope()

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                result.uriContent?.let {
                    var imageUri = result.uriContent
                    if (imageUri != null) {
                        imageScope.launch {

                            bitmapToString = BitMapToString(uriToBitMap(context, imageUri)!!)
                            bitmap = StringToBitMap(bitmapToString)
                            println("bitmapToString $bitmapToString")
                        }
                    }
                }
            } else {
                //If something went wrong you can handle the error here
                println("ImageCropping error: ${result.error}")
            }
        }


    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA,
        onPermissionResult = { granted ->
            if (granted) {
                val cropOptions = CropImageContractOptions(
                    null, CropImageOptions(
                        imageSourceIncludeGallery = false,
                        toolbarColor = appThemePrimary.toArgb(),
                        toolbarBackButtonColor = android.graphics.Color.WHITE
                    )
                )
                imageCropLauncher.launch(cropOptions)
            } else {
                var intent = Intent()
                intent = intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                redirectAppSetting.launch(intent)
            }
        })


    Dialog(onDismissRequest = outSide) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(appThemePrimary),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(appThemePrimary)
                        .padding(all = 12.dp),

                    ) {
                    Text(text = if (title == "") {
                        "Alert"
                    } else {
                        title
                    }, fontSize = 18.sp, color = Color.White, modifier = Modifier.clickable {
                        header = "This is Header message sample desc new content is add"
                        description =
                            "Some desc  message sample desc new content is add successfully more context message "
                    }

                    )
                }

                IconButton(
                    onClick = onDismissCloseIcons, Modifier.background(appThemePrimary)
                ) {
                    Icon(
                        Icons.Default.Close, contentDescription = "", tint = Color.White,
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                    .background(Color.White)
                    .padding(15.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                TextFieldOutLine(
                    value = header,
                    onChange = { data ->
                        headerError = false
                        header = data

                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Heading",
                    placeholder = "Heading (not exceeding 8 wards)"
                )

                Text(
                    text = if (headerError) {
                        "Heading (not exceeding 8 wards)"
                    } else {
                        ""
                    },

                    fontSize = 11.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 3.dp, top = 3.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))

                TextFieldOutLine(
                    value = description,
                    onChange = { data ->
                        description = data
                        descriptionError = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Description",
                    placeholder = "Description (not exceeding 20 wards)",
                    maxLines = 4,
                    minLines = 4
                )
                Text(
                    text = if (descriptionError) {
                        "Description (not exceeding 20 wards)"
                    } else {
                        ""
                    },
                    fontSize = 11.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 3.dp, top = 3.dp)
                )

                Box(
                    modifier = Modifier

                        .waterfallPadding()
                        .border(
                            width = 1.dp,
                            color = appThemeTintGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp)), Alignment.Center
                ) {
                    Column {
                        if (bitmap != null) {

                            Image(
                                bitmap = bitmap?.asImageBitmap()!!,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .background(Color.Blue)
                                    .size(210.dp, 110.dp)
                                    .clickable {
                                        imageError = false
                                        showImageSelect = true
//                                        cameraPermissionState.launchPermissionRequest()
                                    },
                            )

                        } else {
                            Image(
                                imageVector = Icons.Default.UploadFile, contentDescription = null,
                                modifier = Modifier

                                    .size(110.dp, 110.dp)
                                    .clickable {
                                        imageError = false
                                        showImageSelect = true
                                    },
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                    appThemeTintGray
                                ),
                            )
                        }
                    }
                }
                Text(
                    text = if (imageError) {
                        "Please upload image"
                    } else {
                        ""
                    },
                    fontSize = 11.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 3.dp, top = 3.dp)
                )


                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        if (header.length < 8) {
                            headerError = true
                        } else if (description.length < 8) {
                            descriptionError = true
                        } else if (bitmap == null) {
                            imageError = true
                        } else {


                            println("images $bitmapToString")
                            submit(PostModel(header, description, bitmapString = bitmapToString))
                        }
                    },
                    modifier = Modifier.width(110.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = appThemePrimary80)

                ) {
                    Text(if (!isUpdate) " Add " else " Update ")
                }

                Spacer(modifier = Modifier.height(15.dp))
            }

            showImageSelect(showImageSelect, selected = {
                showImageSelect = false
                if (it) cameraPermissionState.launchPermissionRequest()
                else {
                    val cropOptions = CropImageContractOptions(
                        null, CropImageOptions(
                            imageSourceIncludeCamera = false,
                            toolbarColor = appThemePrimary.toArgb(),
                            toolbarBackButtonColor = android.graphics.Color.WHITE
                        )
                    )
                    imageCropLauncher.launch(cropOptions)
                }

            }, outSide = {


            }, cancel = {
                showImageSelect = false
            })

        }
    }
}


@Composable
fun showImageSelect(
    show: Boolean = false,
    selected: (isCamera: Boolean) -> Unit,
    outSide: () -> Unit,
    cancel: () -> Unit,
) {
    if (show) {
        Dialog(onDismissRequest = outSide) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appThemePrimary),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(appThemePrimary)
                            .padding(all = 12.dp),

                        ) {
                        Text(

                            "Browse Image By", fontSize = 18.sp, color = Color.White
                        )
                    }

                    IconButton(
                        onClick = cancel, Modifier.background(appThemePrimary)
                    ) {
                        Icon(
                            Icons.Default.Close, contentDescription = "", tint = Color.White,
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                        .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
                        .background(Color.White)
                        .padding(start = 10.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp)
                            .background(Color.White)
                            .clickable {

                                selected(true)
                            },

                        ) {

                        Icon(
                            imageVector = Icons.Default.Camera,
                            contentDescription = "",
                            tint = appThemeAccident
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            " Camera", color = Color.Black, fontSize = 16.sp
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(all = 10.dp)
                            .clickable {

                                selected(false)
                            },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "",
                            tint = appThemeAccident
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            " Gallery", color = Color.Black, fontSize = 16.sp
                        )
                    }


                    Spacer(modifier = Modifier.height(15.dp))
                }

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreviewsAlerts() {


    showImageSelect(show = true, selected = {

    }, outSide = { }) {

    }

//    createPostAlerts(outSide = {
//
//        println("out side")
//    }, submit = { it ->
//
//    }, onDismissCloseIcons = {
//
//
//    }, title = ""
//    )


}





