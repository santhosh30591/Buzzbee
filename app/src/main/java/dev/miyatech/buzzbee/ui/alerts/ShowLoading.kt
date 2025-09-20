package dev.miyatech.buzzbee.ui.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.miyatech.buzzbee.ui_components.LoadingAnimation

@Composable
fun ShowLoading(
    isLoading: Boolean,
    submit: () -> Unit,
) {
    if (isLoading) {
        Dialog(
            onDismissRequest = submit,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 10.dp))
                    .padding(all = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingAnimation()
                Spacer(modifier = Modifier.height(15.dp))

                Text("Please wait... ", fontSize = 18.sp, color = Color.White)


            }
        }
    }
}