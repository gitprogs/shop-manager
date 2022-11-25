package com.network.shopmanager.utils

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.network.shopmanager.data.sharedpref.Pref
import com.network.shopmanager.ui.BaseActivity
import com.network.shopmanager.ui.MainActivity
import com.network.shopmanager.ui.SignInActivity
import kotlinx.coroutines.DelicateCoroutinesApi

object Objects {
    @DelicateCoroutinesApi
    lateinit var APP: BaseActivity
    @SuppressLint("StaticFieldLeak")
    lateinit var NETWORK: NetworkHelper
  //  lateinit var DB_LOCAL: MyRoom

    // firebase
    val AUTH: FirebaseAuth = Firebase.auth
    val PREF by lazy { Pref(APP) }


}

