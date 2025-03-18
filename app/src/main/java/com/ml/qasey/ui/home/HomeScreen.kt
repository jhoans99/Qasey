package com.ml.qasey.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ml.qasey.ui.navigation.Home

@Composable
fun HomeRoute() {

}

@Composable
fun HomeScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeBody(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp))
        }
    }
}

@Composable
fun HomeBody(modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {

    }
}