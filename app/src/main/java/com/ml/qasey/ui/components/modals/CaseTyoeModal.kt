package com.ml.qasey.ui.components.modals

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun CaseTypeModal() {
    Dialog(
        onDismissRequest = {

        }
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = {

                },
                modifier = Modifier,
            )
        }
    }
}

@Composable
fun CheckBoxItem() {
    Row {  }
}