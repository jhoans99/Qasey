package com.ml.qasey.ui.dashboard.customer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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


@Composable
fun CustomerDashboardRoute(
    viewModel: DashboardCustomerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
        modifier = modifier.fillMaxSize()
    ) {
        val (inputCase, buttonInitCase) = createRefs()

        SimpleInputText(
            modifier = Modifier.constrainAs(inputCase) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = stringResource(id = R.string.label_entry_case_number),
            value = "",
            onTextChange = {
                if(it.length == 8 ) {
                    viewModel.startTimer()
                }
            }
        )



        PrimaryButton(
            modifier = Modifier.constrainAs(buttonInitCase) {
               top.linkTo(inputCase.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)
            },
            text = stringResource(id = R.string.text_button_init_case)
        ) {
            //TODO: Iniciar caso
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

