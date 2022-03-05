package com.caffeine.eirmon.service.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val uid : String = "",
    val name : String = "",
    val email : String = "",
    val password : String = "",
    val picture : String = ""
) : Parcelable
