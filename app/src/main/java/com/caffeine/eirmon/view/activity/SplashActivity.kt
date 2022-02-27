package com.caffeine.eirmon.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.caffeine.eirmon.R
import com.caffeine.eirmon.util.Constants

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (Constants.currentUser != null){
                Constants.intentToActivity(this, DashboardActivity::class.java)
            }
            else Constants.intentToActivity(this, AuthenticationActivity::class.java)
        }, 0)
    }
}