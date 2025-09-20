package dev.miyatech.buzzbee.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.miyatech.buzzbee.ui_components.HomeTitleBarBack
import dev.miyatech.buzzbee.ui_components.StringToBitMap


@Composable
fun ZoomImage(
    navController: NavController = rememberNavController(), img: String? = null
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }


    val density = LocalDensity.current.density
    val maxScale = 5f // Example maximum scale
    val minScale = 1f // Example minimum scale

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    try {
        bitmap = StringToBitMap(img)
    } catch (e: Exception) {
        println("image uri error $e")
    }

    val state = TransformableState(onTransformation = { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(minScale, maxScale)
        offset += panChange / density // Adjust pan by density for smoother movement
    })


    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        HomeTitleBarBack(text = "Zooming", navController)

        Box(
            modifier = Modifier
                .weight(1f)
                .transformable(state = state)

        ) {


            if (bitmap == null) {
                Image(imageVector = Icons.Default.Mail,
                    contentDescription = "Zoom Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                        })
            } else {
                Image(

                    bitmap = bitmap?.asImageBitmap()!!,

                    contentDescription = "Zoom Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offset.x
                            translationY = offset.y
                        })
            }
//                Image(
//             = Icons.Default.Mail, // Load your image here
//            contentDescription = "Zoomable Image",
//            modifier = Modifier.fillMaxSize()
//        )

//        Image(
//            bitmap = bitmap, // Load your image here
//            contentDescription = "Zoomable Image",
//            modifier = Modifier.fillMaxSize()
//        )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ZoomableImageView() {
    ZoomImage()
}