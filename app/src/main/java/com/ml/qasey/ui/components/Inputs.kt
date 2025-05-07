package com.ml.qasey.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ml.qasey.ui.theme.DarkBlue
import com.ml.qasey.ui.theme.QaseyTheme
import com.ml.qasey.ui.theme.white

@Composable
fun SimpleInputText(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    leadingIcon: ImageVector? = null,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(text = label)
        },
        leadingIcon = {
            when {
                leadingIcon != null -> {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = DarkBlue,
            unfocusedTextColor = DarkBlue,
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            focusedLabelColor = DarkBlue,
            unfocusedIndicatorColor = DarkBlue,
            focusedIndicatorColor = DarkBlue
        )
    )
}


@Composable
fun SimpleInputText(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(text = label)
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = DarkBlue,
            unfocusedTextColor = DarkBlue,
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            focusedLabelColor = DarkBlue,
            unfocusedIndicatorColor = DarkBlue,
            focusedIndicatorColor = DarkBlue
        )
    )
}

@Composable
fun SimpleInputTextPassword(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onTextChange: (String) -> Unit
) {
    var isShowPassword by rememberSaveable {
        mutableStateOf(false)
    }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            onTextChange(it)
        },
        label = {
            Text(text = label)
        },
        trailingIcon = {
            IconButton(onClick = {
                isShowPassword = !isShowPassword
            }
            ) {
                Icon(
                    imageVector = if(isShowPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = null,
                    tint = DarkBlue
                )
            }

        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Key,
                contentDescription = null)
        },
        visualTransformation = if(isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedTextColor = DarkBlue,
            unfocusedTextColor = DarkBlue,
            focusedContainerColor = white,
            unfocusedContainerColor = white,
            focusedLabelColor = DarkBlue,
            unfocusedIndicatorColor = DarkBlue,
            focusedIndicatorColor = DarkBlue
        ),

    )
}

@Preview(showBackground = true)
@Composable
fun InputsPreview() {
    QaseyTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)) {
            SimpleInputTextPassword(label = "Password", value = "1234") {
                
            }
        }
    }
}