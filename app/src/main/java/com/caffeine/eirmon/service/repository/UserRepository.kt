package com.caffeine.eirmon.service.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.Constants
import com.caffeine.eirmon.util.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class UserRepository : UserInterface {

    override suspend fun getUsers(userMutableLiveData: MutableLiveData<DataState<ArrayList<UserModel>>>) {
        userMutableLiveData.postValue(DataState.Loading())
        val tempList = ArrayList<UserModel>()
        Constants.userReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tempList.clear()
                for (ds in snapshot.children){
                    if (ds.child("uid").getValue(String::class.java) != FirebaseAuth.getInstance().uid){
                        ds.getValue(UserModel::class.java)?.let { tempList.add(it) }
                    }
                }
                userMutableLiveData.postValue(DataState.Success(tempList))
            }

            override fun onCancelled(error: DatabaseError) {
                userMutableLiveData.postValue(DataState.Failed(error.message))
            }

        })
    }
}