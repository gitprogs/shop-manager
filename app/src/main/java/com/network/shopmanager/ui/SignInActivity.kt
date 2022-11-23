package com.network.shopmanager.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.network.shopmanager.databinding.ActivitySignInBinding
import com.network.shopmanager.utils.NetworkHelper
import com.network.shopmanager.utils.Objects.APP
import com.network.shopmanager.utils.Objects.NETWORK
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP = this
    }
}