package com.network.shopmanager.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.network.shopmanager.databinding.ActivitySignInBinding
import com.network.shopmanager.utils.NetworkHelper
import com.network.shopmanager.utils.Objects.APP_SIGN_IN
import com.network.shopmanager.utils.Objects.NETWORK

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_SIGN_IN = this
        NETWORK = NetworkHelper(this)
    }
}