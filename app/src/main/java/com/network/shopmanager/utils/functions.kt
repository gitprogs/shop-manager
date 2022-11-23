@file:OptIn(DelicateCoroutinesApi::class)

package com.network.shopmanager.utils

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.CountDownTimer
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("HardwareIds")
fun getDeviceId() =
    Settings.Secure.getString(APP.contentResolver, Settings.Secure.ANDROID_ID)

@DelicateCoroutinesApi
fun noInternetToast() {
    "Internet yo'q".toToast()
}


fun setNightMode(mode: Boolean) {
    if (mode) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        APP.delegate.applyDayNight()
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        APP.delegate.applyDayNight()
    }
}
 fun isDarkModeOn(): Boolean {
    val currentNightMode = APP.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}

fun waitMoment(i: Long = 2000L, function: () -> Unit) {
    val timer = object : CountDownTimer(i, 500) {
        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
            function()
        }
    }
    timer.start()
}


fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)

fun AppCompatActivity.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermissionsCompat(
    permissionsArray: Array<String>,
    requestCode: Int
) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}

fun View.showSnackbar(msgId: Int, length: Int) {
    showSnackbar(context.getString(msgId), length)
}

fun View.showSnackbar(msg: String, length: Int) {
    showSnackbar(msg, length, null, {})
}

fun View.showSnackbar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackbar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun View.showSnackbar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackbar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackbar.setAction(actionMessage) {
            action(this)
        }.show()
    }
}