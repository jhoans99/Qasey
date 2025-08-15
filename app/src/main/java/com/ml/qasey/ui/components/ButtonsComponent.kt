package com.ml.qasey.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ml.qasey.ui.theme.DarkBlue
import com.ml.qasey.ui.theme.white

@Composable
fun PrimaryButton(
    modifier: Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClickAction: () -> Unit
) {
    Button(
        onClick = {
          onClickAction()
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkBlue,
            contentColor =  white
        ),
        enabled = isEnabled
    ) {
        Text(text = text)
    }
}