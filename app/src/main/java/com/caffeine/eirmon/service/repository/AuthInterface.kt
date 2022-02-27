package com.caffeine.eirmon.service.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.DataState

interface AuthInterface {

    fun loginUser(loginMutableLiveData: MutableLiveData<DataState<String>>, email : String, password : String)

    fun createUser(
        createMutableLiveData: MutableLiveData<DataState<String>>,
        name : String,
        email : String,
        password : String,
        picture : String
    )

    fun sendDataToFirebase(
        createMutableLiveData: MutableLiveData<DataState<String>>,
        uid : String,
        name : String,
        email : String,
        password : String,
        picture : String
    )
}