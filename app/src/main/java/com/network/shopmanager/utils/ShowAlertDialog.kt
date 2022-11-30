package com.network.shopmanager.utils

import android.app.AlertDialog
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class ShowAlertDialog(title: String = "Diqqat !", message: String, funtion: (Boolean) -> Unit) {
    init {
        AlertDialog.Builder(APP)
            .setCancelable(true)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ha") { p0, p1 ->
                funtion(true)
            }
            .setNegativeButton("Yo'q") { a, d ->
                funtion(false)
            }
            .create()
            .show()
    }
}