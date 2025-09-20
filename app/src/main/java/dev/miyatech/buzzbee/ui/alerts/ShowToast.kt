package dev.miyatech.buzzbee.ui.alerts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemePrimary80

@Composable
fun ShowToast(
    showToast: Boolean = false,
    outSide: () -> Unit,
    onDismiss: () -> Unit,
    onDismissCloseIcons: () -> Unit,
    title: String,
    subTitle: String
) {
    if (showToast) {
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
                            text = if (title == "") {
                                "Alert"
                            } else {
                                title
                            }, fontSize = 18.sp, color = Color.White
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
                        .background(Color.White),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        subTitle,
                        color = Color.Black,
                        modifier = Modifier.padding(all = 18.dp),
                        fontSize = 15.sp
                    )
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.width(110.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = appThemePrimary80)

                    ) {
                        Text(" OK ")
                    }

                    Spacer(modifier = Modifier.height(15.dp))
                }

            }
        }
    }
}