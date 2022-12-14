package com.network.shopmanager.utils

import android.app.AlertDialog
import android.view.LayoutInflater
import com.network.shopmanager.databinding.ProgressBarBinding
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MyProgressBar {
    private var alert: AlertDialog? = null
    private var view: ProgressBarBinding? = null
    fun startProgress() {
        view = ProgressBarBinding.inflate(LayoutInflater.from(APP), null, false)
        val progressDoalog = AlertDialog.Builder(APP)
        progressDoalog.setView(view?.root)
        progressDoalog.setCancelable(true)
        alert = progressDoalog.create()
        alert?.show()
    }

    fun stopBar() {
        alert?.dismiss()
        alert = null
    }

    fun setText(text: String) {
        view?.tvProgress?.text = text
    }
}