package com.caffeine.eirmon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.caffeine.eirmon.service.repository.AuthRepository
import com.caffeine.eirmon.util.DataState

class AuthViewModel : ViewModel() {

    val repository : AuthRepository = AuthRepository()

    private val loginMutableLiveData : MutableLiveData<DataState<String>> = MutableLiveData<DataState<String>>()
    val loginLiveData : LiveData<DataState<String>>
        get() = loginMutableLiveData

    private val createUserMutableLiveData : MutableLiveData<DataState<String>> = MutableLiveData<DataState<String>>()
    val createUserLiveData : LiveData<DataState<String>>
        get() = createUserMutableLiveData

    fun loginUser(email : String, password : String){
        repository.loginUser(loginMutableLiveData, email, password)
    }

    fun createUser (
        name : String,
        email : String,
        password : String,
        picture : String
    ){
        repository.createUser(createUserMutableLiveData, name, email, password, picture)
    }
}