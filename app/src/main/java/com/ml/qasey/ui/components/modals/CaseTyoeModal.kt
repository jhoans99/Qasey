package com.ml.qasey.ui.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ml.qasey.ui.components.PrimaryButton
import com.ml.qasey.ui.theme.white

@Composable
fun CaseTypeModal(
    onSelectedType: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier
                .background(white)
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            val selectedIndex = remember { mutableStateOf<Int?>(null) }
            val options = listOf("Clasificar", "Derivar", "Asincronico", "Clasificado Derivado", "Bug")
            var selectType = ""
            options.forEachIndexed { index, label ->
                CheckBoxItem(
                    label,
                    Modifier,
                    index,
                    selectedIndex
                ) {
                    selectType = it
                }
            }

            PrimaryButton(
                modifier = Modifier.padding(top = 20.dp),
                text = "Enviar"
            ) {
                onSelectedType(selectType)
            }

        }
    }
}


@Composable
fun CheckBoxItem(
    label: String,
    modifier: Modifier,
    index: Int,
    selectIndex: MutableState<Int?>,
    onSelectedType: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
       Checkbox(
           checked = selectIndex.value == index,
           onCheckedChange = {
                selectIndex.value = if(selectIndex.value == index) null else index
                onSelectedType(label)
           }
       )
        Text(
            text = label
        )
    }
}