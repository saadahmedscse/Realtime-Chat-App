package com.caffeine.eirmon.service.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.DataState

interface UserInterface {
    suspend fun getUsers(userMutableLiveData: MutableLiveData<DataState<ArrayList<UserModel>>>)
    fun getMyDetails(myMutableLiveData: MutableLiveData<DataState<UserModel>>)
    fun updateProfile(profileMutableLiveData: MutableLiveData<DataState<String>>, user : UserModel, name : String, picture : Uri)
}