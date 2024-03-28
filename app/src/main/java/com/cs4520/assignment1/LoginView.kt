package com.cs4520.assignment1

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun LoginView(viewModel: LoginViewModel) {
    val name = viewModel.nameLiveData.observeAsState()
    val password = viewModel.passwordLiveData.observeAsState()
    val error = viewModel.errorLiveData.observeAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(name.value!!,
            onValueChange = viewModel.onNameChanged,
            label = { Text("Name") })
        TextField(password.value!!,
            onValueChange = viewModel.onPasswordChanged,
            label = { Text("Password") })
        Button(onClick = viewModel.onLoginClicked) {
            Text("Login")
        }

        LaunchedEffect(error.value) {
            error.value?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
