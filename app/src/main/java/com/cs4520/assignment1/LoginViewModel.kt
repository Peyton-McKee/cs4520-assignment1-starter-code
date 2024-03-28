package com.cs4520.assignment1

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class LoginViewModel(private val navController: NavController): ViewModel() {
    val nameLiveData: MutableLiveData<String> = MutableLiveData<String>("")


    val passwordLiveData: MutableLiveData<String> = MutableLiveData<String>("")


    val errorLiveData: MutableLiveData<Exception?> by lazy {
        MutableLiveData<Exception?>()
    }

    val login: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)


    val onNameChanged: (String) -> Unit = {
        Log.d("LoginViewModel", "Name changed to $it")
        nameLiveData.setValue(it)
    }

    val onPasswordChanged: (String) -> Unit = {
        passwordLiveData.setValue(it)
    }

    val onLoginClicked: () -> Unit = {
        if (nameLiveData.value == "admin" && passwordLiveData.value == "admin") {
            navController.navigate("productList")
        } else {
            errorLiveData.setValue(Exception("Invalid credentials"))
        }
    }
}