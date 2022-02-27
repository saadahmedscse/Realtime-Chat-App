package com.caffeine.eirmon.view.util

import android.app.Activity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Constants {
    const val app_name = "Eirmon"
    val reference = FirebaseDatabase.getInstance().reference.child(app_name)
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val UID = auth.uid

    fun intentToActivity(activity : Activity, c : Class<*>){
        activity.startActivity(Intent(activity, c))
        activity.finish()
    }
}