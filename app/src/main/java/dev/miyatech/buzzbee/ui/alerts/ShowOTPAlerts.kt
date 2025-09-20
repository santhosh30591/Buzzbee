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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import java.util.stream.Collectors

@Composable
fun ShowOTPAlerts(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit,
    submit: () -> Unit,
    close: () -> Unit,
    otp_code: String
) {
    var code: List<Char> by remember {
        mutableStateOf(listOf())
    }

    var otp_error by remember {
        mutableStateOf(false)
    }

    var otp_error_btn by remember {
        mutableStateOf(false)
    }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }




    Dialog(onDismissRequest = submit) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                            text = "OTP", fontSize = 18.sp, color = Color.White
                        )
                    }


                    IconButton(
                        onClick = close, Modifier.background(appThemePrimary)
                    ) {
                        Icon(
                            Icons.Default.Close, contentDescription = "", tint = Color.White
                        )
                    }
                }


                Spacer(modifier = Modifier.height(15.dp))

                Row(modifier = modifier.padding(start = 16.dp)) {
                    (0 until length).forEach { index ->
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 2.dp)

                                .focusRequester(focusRequesters[index]),
                            shape = RoundedCornerShape(5.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center),
//                textStyle = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center),
                            singleLine = true,
                            value = code.getOrNull(index)?.takeIf { it.isDigit() }?.toString()
                                ?: "",
                            onValueChange = { value: String ->

                                otp_error = false
                                otp_error_btn = false
                                if (focusRequesters[index].freeFocus()) {   //For some reason this fixes the issue of focusrequestor causing on value changed to call twice
                                    val temp = code.toMutableList()
                                    if (value == "") {
                                        if (temp.size > index) {
                                            temp.removeAt(index)
                                            code = temp
                                            focusRequesters.getOrNull(index - 1)?.requestFocus()
                                        }
                                    } else {


                                        if (code.size > index) {
                                            temp[index] = value.getOrNull(0) ?: ' '
                                        } else if (value.getOrNull(0)?.isDigit() == true) {
                                            temp.add(value.getOrNull(0) ?: ' ')
                                            code = temp
                                            focusRequesters.getOrNull(index + 1)?.requestFocus()
                                                ?: onFilled(
                                                    code.joinToString(separator = "")
                                                )


                                        }
                                    }
                                }
                                println("Code $code and otp_code $otp_code " + code.size)

                                if (code.size == (otp_code.length)) {

                                    println("Code6 $code and otp_code $otp_code " + code.size)
                                    val enter_code: String = code.stream()
                                        .map(java.lang.String::valueOf) // Convert each Character to a String
                                        .collect(Collectors.joining())
                                    otp_error = !enter_code.equals(otp_code)
                                    otp_error_btn = enter_code.equals(otp_code)
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                            ),

                            )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }


                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(18.dp)
                        .padding(start = 15.dp, top = 3.dp),
                    text = if (otp_error) "Invalid OTO" else "",
                    fontSize = 12.sp,
                    color = Color.Red
                )


                Spacer(modifier = Modifier.height(8.dp))
                if (otp_error_btn) Button(
                    onClick = submit, modifier = Modifier.width(100.dp)
                ) {
                    Text(" Submit ")
                } else {

                    Button(

                        onClick = {


                            val enter_code: String = code.stream()
                                .map(java.lang.String::valueOf) // Convert each Character to a String
                                .collect(Collectors.joining())
                            println("Code $code and otp_code $otp_code")


                            if (enter_code.equals(otp_code)) {
                                otp_error_btn = true
                            } else {
                                otp_error = true
                            }

                        }, modifier = Modifier.width(100.dp)
                    ) {
                        Text(" Submit ")
                    }
                }
            }
        }
    }
}
