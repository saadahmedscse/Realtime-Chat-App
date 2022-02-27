package com.caffeine.eirmon.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import com.caffeine.eirmon.R

object AlertDialog {
    private var instance : AlertDialog? = null
    private var dialog : Dialog? = null

    fun getInstance(context: Context) : AlertDialog{
        if (instance == null){
            instance = AlertDialog
            dialog = Dialog(context)
        }
        return instance as AlertDialog
    }

    fun showAlertDialog(msg : String, action : String){
        dialog?.setContentView(R.layout.alert_dialog)

        val message = dialog?.findViewById<TextView>(R.id.popup_message)
        val actionBtn = dialog?.findViewById<TextView>(R.id.action_ok)

        message?.text = msg
        actionBtn?.text = action

        actionBtn?.setOnClickListener{
            dialog?.dismiss()
        }

        dialog?.setCancelable(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.show();
    }
}