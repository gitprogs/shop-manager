package com.network.shopmanager.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

fun String.validateEmail() = if (this.contains("@")) this else this + "@gmail.com"

@DelicateCoroutinesApi
fun String.toToast() {
    Toast.makeText(APP, this, Toast.LENGTH_SHORT).show()
}


@DelicateCoroutinesApi
fun hideKeyBoard() {
    val view = APP.currentFocus
    if (view != null) {
        val inm: InputMethodManager =
            APP.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@SuppressLint("SimpleDateFormat")
fun Long.toDate(): String = SimpleDateFormat("dd.MM.yyyy").format(Date(this))


@SuppressLint("SimpleDateFormat")
fun Long.huminize(): String {
    val time = (System.currentTimeMillis() - this) / 60_000
    if (time < 5) {
        return "Hozirgina"
    } else if (time < 60) {
        return "$time min odin"
    } else if (time < (60 * 24)) {
        return "${time / 60} soat odin"
    } else if (time < (60 * 24 * 30)) {
        return "${time / (60 * 24)} kun odin"
    } else if (time > 60 * 24 * 30) {
        return this.toDate()
    } else return "-"
}