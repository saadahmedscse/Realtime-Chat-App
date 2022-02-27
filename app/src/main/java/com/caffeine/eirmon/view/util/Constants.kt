package com.caffeine.eirmon.view.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Constants {
    const val app_name = "Eirmon"
    val reference = FirebaseDatabase.getInstance().reference.child(app_name)
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val UID = auth.uid
    const val LONG = 1
    const val SHORT = 0

    fun intentToActivity(activity : Activity, c : Class<*>){
        activity.startActivity(Intent(activity, c))
        activity.finish()
    }

    fun showToast(context : Context, message : String, duration : Int){
        Toast.makeText(context, message, duration).show()
    }
}