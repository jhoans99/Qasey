package com.ml.qasey.ui.dashboard.customer

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import com.ml.qasey.ui.components.Loader
import com.ml.qasey.ui.components.modals.CaseTypeModal
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
            modifier = Modifier.padding(paddingValues)
        ) {
            CustomerDashboardBody(
                modifier = Modifier.padding(horizontal = 10.dp),
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
    ConstraintLayout(
        modifier = modifier.fillMaxSize().padding(top = 30.dp)
    ) {
        val (inputCase, buttonInitCase, timerText) = createRefs()
        val (listHistoryCases) = createRefs()

        SimpleInputText(
            modifier = Modifier.constrainAs(inputCase) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
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
                    modifier = Modifier.constrainAs(timerText) {
                        top.linkTo(inputCase.bottom, 15.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }


        PrimaryButton(
            modifier = Modifier.constrainAs(buttonInitCase) {
                when {
                    uiState.timer != 0 -> top.linkTo(timerText.bottom)
                    else -> top.linkTo(inputCase.bottom)
                }
               start.linkTo(parent.start)
               end.linkTo(parent.end)
            },
            text = stringResource(id = R.string.text_button_finish_case)
        ) {
            viewModel.stopTimer()
            viewModel.resetTimer()
            viewModel.onShowModalTypeCase(true)
        }


        when {
            uiState.historyCaseList.isNotEmpty() -> {
                HistoryCasesByUser(Modifier.constrainAs(listHistoryCases) {
                    top.linkTo(buttonInitCase.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },uiState)
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

        LazyColumn {
            items(uiState.historyCaseList) {
                Text(it.numberCase)
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomerDashboardPreview() {
    QaseyTheme {
        CustomerDashboardRoute()
    }
}

