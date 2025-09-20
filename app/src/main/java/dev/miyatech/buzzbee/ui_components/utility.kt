package dev.miyatech.buzzbee.ui_components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.miyatech.buzzbee.model.StateList
import dev.miyatech.buzzbee.ui.alerts.createPostAlerts
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Base64


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetStateList(
    title: String,
    list: ArrayList<StateList>,
    onDismiss: () -> Unit,
    onItemSelected: (StateList) -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(horizontal = 5.dp),
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Select " + title,
                    fontSize = 18.sp, color = appThemeAccident,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 5.dp, bottom = 8.dp),

                    )

                LazyColumn {
                    items(list.size) { item ->

                        Column(

                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    onItemSelected(list.get(item))
                                }

                        ) {
                            Text(
                                list.get(item).name, fontSize = 14.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp
                                    ),
                            )

                            Text(
                                "",
                                modifier = Modifier
                                    .height(1.dp)
                                    .background(appThemeTintGray)
                                    .fillMaxWidth()
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        })
}


fun shareString(context: Context, textToShare: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND // Specifies the action to perform (sending data)
        putExtra(Intent.EXTRA_TEXT, textToShare) // Adds the string data to the Intent
        type = "text/plain" // Defines the MIME type of the data being shared
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}


@SuppressLint("NewApi")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreviewsAlerts() {

//    ShowOTPAlerts(length = 4, onFilled = {}, submit = {
//    }, close = {
//
//    }, otp_code = "1234"
//    )

    createPostAlerts(outSide = {

        println("out side")
    }, submit = { it ->

    }, onDismissCloseIcons = {


    }, title = ""
    )

}


fun uriToBitMap(context: Context, imageUri: Uri?): Bitmap? {
    var bitmap: Bitmap? = null
    if (imageUri != null) {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
            bitmap = ImageDecoder.decodeBitmap(source)
        }

    }
    return bitmap;

}


fun saveBitmapToFile( bitmap: Bitmap?): Boolean {

   var file= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Buzzbee")
    var outputStream: FileOutputStream? = null
    try {
        outputStream = FileOutputStream(file)
        bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream!!.flush()
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    } finally {
        outputStream?.close()
    }
}

@SuppressLint("NewApi")
fun BitMapToString(bitmap: Bitmap): String {
    val base = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, base)
    val b = base.toByteArray()
    return Base64.getEncoder().encodeToString(b)
}


fun StringToBitMap(encodedString: String?): Bitmap? {


    val imageBytes = android.util.Base64.decode(encodedString, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

}









