package com.network.shopmanager.utils

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.network.shopmanager.ui.SignInActivity

object Objects {
    lateinit var APP_SIGN_IN: SignInActivity
    @SuppressLint("StaticFieldLeak")
    lateinit var NETWORK: NetworkHelper
  //  lateinit var DB_LOCAL: MyRoom

    // firebase
    val AUTH: FirebaseAuth = Firebase.auth
}