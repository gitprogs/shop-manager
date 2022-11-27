package com.network.shopmanager.utils

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.network.shopmanager.data.room.MyRoom
import com.network.shopmanager.data.sharedpref.Pref
import com.network.shopmanager.ui.activities.BaseActivity
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
object Objects {
    @DelicateCoroutinesApi
    lateinit var APP: BaseActivity
    @SuppressLint("StaticFieldLeak")
    lateinit var NETWORK: NetworkHelper
    lateinit var DB_LOCAL: MyRoom

    // firebase
    val AUTH: FirebaseAuth = Firebase.auth
    val PREF by lazy { Pref(APP) }


}

