package com.cs4520.assignment1

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val navController: NavController): ViewModel() {
    private val _nameLiveData: MutableStateFlow<String> = MutableStateFlow("")
    val nameLiveData: StateFlow<String> = _nameLiveData

    private val _passwordLiveData: MutableStateFlow<String> = MutableStateFlow("")
    val passwordLiveData: StateFlow<String> = _passwordLiveData

    private val _errorLiveData: MutableStateFlow<Exception?> = MutableStateFlow(null)
    val errorLiveData: StateFlow<Exception?> = _errorLiveData

    val onNameChanged: (String) -> Unit = {
        Log.d("LoginViewModel", "Name changed to $it")
        _nameLiveData.value = it
    }

    val onPasswordChanged: (String) -> Unit = {
        _passwordLiveData.value = it
    }

    val onLoginClicked: () -> Unit = {
        if (nameLiveData.value == "admin" && passwordLiveData.value == "admin") {
            navController.navigate("productList")
        } else {
            _errorLiveData.value = Exception("Invalid credentials")
        }
    }
}