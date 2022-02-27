package com.caffeine.eirmon.service.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.Constants
import com.caffeine.eirmon.util.DataState
import com.google.firebase.auth.FirebaseAuth

class AuthRepository : AuthInterface{
    override fun loginUser(loginMutableLiveData: MutableLiveData<DataState<String>>, email: String, password: String) {
        loginMutableLiveData.postValue(DataState.Loading())
        Constants.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    loginMutableLiveData.postValue(DataState.Success())
                }
                else{
                    loginMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }

    override fun createUser(
        createMutableLiveData: MutableLiveData<DataState<String>>,
        name: String,
        email: String,
        password: String,
        picture: String
    ) {
        createMutableLiveData.postValue(DataState.Loading())
        Constants.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    sendDataToFirebase(
                        createMutableLiveData,
                        Constants.auth.uid!!,
                        name,
                        email,
                        password,
                        picture
                    )
                }
                else {
                    createMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }

    override fun sendDataToFirebase(
        createMutableLiveData: MutableLiveData<DataState<String>>,
        uid: String,
        name: String,
        email: String,
        password: String,
        picture: String
    ) {
        val user = UserModel(uid, name, email, password, picture)
        Constants.userReference.child(Constants.auth.uid!!).setValue(user)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    createMutableLiveData.postValue(DataState.Success())
                }
                else {
                    createMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }
}