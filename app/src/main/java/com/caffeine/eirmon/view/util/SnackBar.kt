package com.caffeine.eirmon.view.util

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class SnackBar {

    private var instance : SnackBar? = null

    fun getSnackInstance() : SnackBar{
        if (instance == null){
            instance = SnackBar()
        }
        return instance as SnackBar
    }

    fun show(view : View, message: String, duration : Int){
        val snackBar = Snackbar.make(view, message, duration)
        snackBar.setAction("Close") {
            snackBar.dismiss()
        }

        snackBar.setActionTextColor(Color.WHITE)
        val snackbarView = snackBar.view

        snackbarView.setBackgroundColor(Color.DKGRAY)
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.LTGRAY)
        snackBar.show()
    }
}