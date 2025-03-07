package com.ml.qasey.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ml.qasey.R
import com.ml.qasey.ui.components.PrimaryButton
import com.ml.qasey.ui.components.SimpleInputText
import com.ml.qasey.ui.components.SimpleInputTextPassword
import com.ml.qasey.ui.theme.QaseyTheme

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreen(uiState)
}

@Composable
fun LoginScreen(uiState: LoginUiState) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LoginBody(
                modifier = Modifier,
                uiState
            )
        }
    }
}

@Composable
fun LoginBody(
    modifier: Modifier,
    uiState: LoginUiState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    ConstraintLayout(
        modifier.fillMaxSize()
    ) {
        val (imageCustomer, inputUserName, inputPassword, buttonLogin) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.img_customer),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .constrainAs(imageCustomer) {
                    top.linkTo(parent.top, 60.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        SimpleInputText(
            modifier = Modifier.constrainAs(inputUserName) {
                top.linkTo(imageCustomer.bottom,30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Username",
            value = uiState.userName,
            leadingIcon = Icons.Outlined.Person
        ) {
            viewModel.updateUserNameValue(it)
        }

        SimpleInputTextPassword(
            modifier = Modifier.constrainAs(inputPassword) {
                top.linkTo(inputUserName.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            label = "Password",
            value = uiState.password
        ) {
            viewModel.updatePasswordValue(it)
        }

        PrimaryButton(modifier = Modifier.constrainAs(buttonLogin) {
            top.linkTo(inputPassword.bottom, 40.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, text = "Iniciar sesión") {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    QaseyTheme {
        LoginRoute()
    }
}