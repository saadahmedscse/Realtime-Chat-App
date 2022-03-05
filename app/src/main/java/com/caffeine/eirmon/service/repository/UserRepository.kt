package com.caffeine.eirmon.service.repository

import android.net.Uri
import android.util.Log
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

    override fun getMyDetails(myMutableLiveData: MutableLiveData<DataState<UserModel>>) {
        myMutableLiveData.postValue(DataState.Loading())
        Constants.userReference.child(Constants.auth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(UserModel::class.java).let {
                    myMutableLiveData.postValue(DataState.Success(it))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                myMutableLiveData.postValue(DataState.Failed(error.message))
            }

        })
    }

    override fun updateProfile(
        profileMutableLiveData: MutableLiveData<DataState<String>>,
        user : UserModel,
        name : String,
        picture : Uri
    ) {
        profileMutableLiveData.postValue(DataState.Loading())
        var url = ""

        if (picture.toString() == "android.resource://com.caffeine.eirmon/2131165367"){
            val updatedUser = UserModel(user.uid, name, user.email, user.password, user.picture)

            Constants.userReference.child(user.uid).setValue(updatedUser)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        profileMutableLiveData.postValue(DataState.Success("Profile updated successfully"))
                    }

                    else{
                        profileMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                    }
                }
        }

        else{
            Constants.storageRef.child(user.uid).putFile(picture)
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        Constants.storageRef.child(user.uid).downloadUrl
                            .addOnSuccessListener { downloadTask ->
                                url = downloadTask.toString()

                                val updatedUser = UserModel(user.uid, name, user.email, user.password, url)

                                Constants.userReference.child(user.uid).setValue(updatedUser)
                                    .addOnCompleteListener{ updateTask ->
                                        if (updateTask.isSuccessful){
                                            profileMutableLiveData.postValue(DataState.Success("Profile updated successfully"))
                                        }

                                        else{
                                            profileMutableLiveData.postValue(DataState.Failed(updateTask.exception?.message))
                                        }
                                    }
                            }
                    }
                    else{
                        profileMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                    }
                }
        }
    }
}