package com.network.shopmanager.utils

import android.content.Intent
import android.provider.Settings
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class Gps {
    fun openGpsSettings() {
        ShowAlertDialog(message = "GPS yoqilmagan. Yoqilsinmi?") {
            if (it) {
                APP.startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 11
                )
            }
        }
    }
}