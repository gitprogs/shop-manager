package com.network.shopmanager.ui.activities

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.network.shopmanager.ui.MainViewModel
import com.network.shopmanager.utils.Constants.KEY_NIGHT_MODE
import com.network.shopmanager.utils.Objects.PREF
import com.network.shopmanager.utils.setNightMode

open class BaseActivity : AppCompatActivity() {
    val vm: MainViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        val mode = PREF.getBoolean(KEY_NIGHT_MODE) ?: false
        setNightMode(mode)
    }
}
