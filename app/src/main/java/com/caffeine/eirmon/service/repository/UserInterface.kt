package com.caffeine.eirmon.service.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.DataState

interface UserInterface {
    suspend fun getUsers(userMutableLiveData: MutableLiveData<DataState<ArrayList<UserModel>>>)
}