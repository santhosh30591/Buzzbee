package dev.miyatech.buzzbee.ui_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import dev.miyatech.buzzbee.ui.theme.appThemeAccident
import dev.miyatech.buzzbee.ui.theme.appThemePrimary
import dev.miyatech.buzzbee.ui.theme.appThemeTintGray


@Composable
fun TextFieldOutLine(

    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "label",
    placeholder: String = "Enter enter ",
    minLines: Int = 1,
    maxLines: Int = 2,


    ) {

    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        modifier = modifier,
        onValueChange = onChange,
        minLines = minLines,
        maxLines = maxLines,
        label = { Text(text = label) },
        placeholder = {
            Text(text = placeholder)


        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
    )
}

@Composable
fun TextFieldWithImage(

    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "label",
    placeholder: String = "Enter enter ",
    icon: ImageVector,
    keyboardType: KeyboardOptions = KeyboardOptions()
) {
    val leadingIcon = @Composable {
        Icon(
            icon, contentDescription = "", tint = appThemePrimary
        )
    }

    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        leadingIcon = leadingIcon,
        modifier = modifier,
        onValueChange = onChange,
        label = { Text(text = label) },
        placeholder = {
            Text(text = placeholder)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = appThemeAccident, // Color when focused
            unfocusedBorderColor = appThemePrimary, // Color when focused
        ),
        singleLine = true,
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardOptions = keyboardType,
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
    )
}

@Composable
fun TextFieldWithImageSelect(

    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "label",
    placeholder: String = "Enter enter ",


    icon: ImageVector,
) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val leadingIcon = @Composable {
        Icon(
            icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary
        )
    }

    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowDown,
                contentDescription = "",
                tint = appThemeTintGray
            )
        }
    }


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,

//        modifier = Modifier.clickable {
//          println("sekjdhbfjkhdbf " )
//        },
        leadingIcon = leadingIcon,
        enabled = false,
        trailingIcon = trailingIcon,

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),


        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = Color.Black,
            disabledBorderColor = appThemePrimary,
            disabledLabelColor = appThemePrimary,
        ),
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            unfocusedBorderColor = appThemePrimary, // Color when focused
//            focusedBorderColor = appThemeAccident, // Color when focused
//        ),
//        keyboardActions = KeyboardActions(onDone = { submit() }),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
    )
}


@Composable
fun PasswordField(

    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "label",
    placeholder: String = "Enter enter ",

    submit: () -> Unit,

    ) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val leadingIcon = @Composable {
        Icon(
            Icons.Default.Lock, contentDescription = "", tint = MaterialTheme.colorScheme.primary
        )
    }

    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }


    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = appThemeAccident, // Color when focused
            unfocusedBorderColor = appThemePrimary, // Color when focused
        ),
        keyboardActions = KeyboardActions(onDone = { submit() }),
        placeholder = { Text(placeholder) },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}


enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick() = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 1.2f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { })
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}
