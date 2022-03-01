package com.caffeine.eirmon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.service.repository.UserRepository
import com.caffeine.eirmon.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UserRepository()

    private var userMutableLiveData = MutableLiveData<DataState<ArrayList<UserModel>>>()
    val userLiveData : LiveData<DataState<ArrayList<UserModel>>>
        get() = userMutableLiveData

    fun getUsers(){
        viewModelScope.launch (Dispatchers.Default) {
            repository.getUsers(userMutableLiveData)
        }
    }
}