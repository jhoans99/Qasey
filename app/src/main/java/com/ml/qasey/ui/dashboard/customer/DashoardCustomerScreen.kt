package com.ml.qasey.ui.dashboard.customer

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ml.qasey.R
import com.ml.qasey.ui.components.PrimaryButton
import com.ml.qasey.ui.components.SimpleInputText
import com.ml.qasey.ui.theme.QaseyTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.ml.qasey.model.CreateCase
import com.ml.qasey.ui.components.Loader
import com.ml.qasey.ui.components.modals.CaseTypeModal
import com.ml.qasey.ui.theme.white
import com.ml.qasey.utils.convertToFormatTime


@Composable
fun CustomerDashboardRoute(
    viewModel: DashboardCustomerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getCasesUser()
    }

    when {
        uiState.isShowModalTypeCase -> {
            CaseTypeModal(
                onSelectedType = {
                    viewModel.onShowModalTypeCase(false)
                    viewModel.saveCase(it)
                },
                onDismiss = {
                    viewModel.onShowModalTypeCase(false)
                }
            )
        }

        uiState.isLoading -> Loader()

        uiState.isFailureCreateCase -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context,"Hubo un error al guardar", Toast.LENGTH_SHORT).show()
            }
        }

        uiState.isSuccessCreateCase -> {
            LaunchedEffect(Unit) {
                Toast.makeText(context,"Se guardo el caso", Toast.LENGTH_SHORT).show()
            }
        }

        uiState.isShowModalEditCase -> {
            Dialog(
                onDismissRequest = {
                    viewModel.onUpdateValueShowEditModal(false)
                },

            ) {
                var inputEditCase by remember {
                    mutableStateOf(uiState.numberCaseEdit)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.background(white).padding(10.dp)
                ) {
                    Text("Editar el número de caso")
                    SimpleInputText(
                        Modifier,
                        label = "Número de caso",
                        inputEditCase
                    ) {
                        inputEditCase = it
                    }
                    PrimaryButton(
                        Modifier.padding(top = 10.dp),
                        "Editar caso"
                    ) {
                        viewModel.onUpdateValueShowEditModal(false)
                        viewModel.updateCaseSelected()
                    }
                }
            }
        }
    }


    CustomerDashboardScreen(
        uiState
    )
}

@Composable
fun CustomerDashboardScreen(uiState: DashboardCustomerUiState) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            CustomerDashboardBody(
                modifier = Modifier.padding(15.dp),
                uiState
            )
        }
    }
}

@Composable
fun CustomerDashboardBody(
    modifier: Modifier,
    uiState: DashboardCustomerUiState,
    viewModel: DashboardCustomerViewModel = hiltViewModel()
    ) {
    Column (
        modifier = modifier.fillMaxSize().padding(top = 30.dp)
    ) {

        SimpleInputText(
            modifier = Modifier.padding(10.dp),
            label = stringResource(id = R.string.label_entry_case_number),
            value = uiState.numberCase,
            onTextChange = {
                viewModel.onValueChangeNumberCase(it)
            }
        )

        when {
            uiState.timer != 0 -> {
                Text(
                    text = uiState.timer.convertToFormatTime(),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(top =  10.dp)
                )
            }
        }


        PrimaryButton(
            modifier = Modifier,
            text = stringResource(id = R.string.text_button_finish_case)
        ) {
            viewModel.stopTimer()
            viewModel.resetTimer()
            viewModel.onShowModalTypeCase(true)
        }


        when {
            uiState.historyCaseList.isNotEmpty() -> {
                HistoryCasesByUser(Modifier.fillMaxWidth().padding(top =  10.dp),uiState)
            }
        }
    }
}

@Composable
fun HistoryCasesByUser(
    modifier: Modifier,
    uiState: DashboardCustomerUiState
) {

    Column(
        modifier = modifier
    ) {
        Text(
            "Historial de casos",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(Modifier) {
            items(uiState.historyCaseList) {
                HistoryCaseItem(Modifier.fillMaxWidth(),it)
            }
        }
    }
}

@Composable
fun HistoryCaseItem(
    modifier: Modifier,
    case: CreateCase,
    viewModel: DashboardCustomerViewModel = hiltViewModel()
) {
    Card(
        modifier = modifier.padding(top = 15.dp, bottom = 5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = white
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                "Número de caso",
                style = MaterialTheme.typography.labelMedium
            )
            Text(case.numberCase)

            Spacer(Modifier.height(10.dp))

            Text(
                "Tipo de caso",
                style = MaterialTheme.typography.labelMedium
            )
            Text(case.typeCase)

            Spacer(Modifier.height(10.dp))

            Text(
                "Fecha y tiempo del caso",
                style = MaterialTheme.typography.labelMedium
            )
            Text("${case.timer}, ${case.endDate}")

            PrimaryButton(Modifier, "Editar") {
                viewModel.onUpdateValueShowEditModal(true)
                viewModel.saveNumberCaseToEdit(case.numberCase)
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomerDashboardPreview() {
    QaseyTheme {
        HistoryCaseItem(Modifier.fillMaxWidth().padding(15.dp), CreateCase(numberCase = "123456", timer = "00:00", "Finalizada", "23/05/2025", ""))
    }
}

