package com.network.shopmanager.ui.activities

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.network.shopmanager.data.room.MyRoom
import com.network.shopmanager.ui.MainViewModel
import com.network.shopmanager.utils.Constants.KEY_NIGHT_MODE
import com.network.shopmanager.utils.Objects.DB_LOCAL
import com.network.shopmanager.utils.Objects.PREF
import com.network.shopmanager.utils.setNightMode
import com.network.shopmanager.utils.toToast
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
open class BaseActivity : AppCompatActivity() {
    val vm: MainViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        val mode = PREF.getBoolean(KEY_NIGHT_MODE) ?: false
        setNightMode(mode)
    }

    fun requestPermissions(vararg permissions: String, function: () -> Unit) {
        Dexter.withContext(this)
            .withPermissions(
                *permissions
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            function()
                        } else {
                            showSettingsDialog(*permissions)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
                Log.d("per", "withErrorListener : ${it.name}, ${it}")
                "Xatolik".toToast()
            }
            .check()
    }


    private fun showSettingsDialog(vararg permissions: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ruxsat kerak !")
        builder.setMessage("${getPermissionNames(*permissions)}larga ruxsat talab etiladi!")
        builder.setPositiveButton("Sozlamalar") { dialog, which ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Bekor") { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun getPermissionNames(vararg permissions: String): String {
        var names = ""
        permissions.forEach {
            when (it) {
                Manifest.permission.CAMERA -> names = "kamera, $names"
                Manifest.permission.REQUEST_INSTALL_PACKAGES -> names = "dastur o'rnatish, $names"
                Manifest.permission.READ_EXTERNAL_STORAGE -> names = "xotiradan o'qish, $names"
                Manifest.permission.WRITE_EXTERNAL_STORAGE -> names = "xotiraga yozish, $names"
                Manifest.permission.ACCESS_COARSE_LOCATION -> names = "geo lokaciya, $names"
                Manifest.permission.ACCESS_FINE_LOCATION -> names = "geo lokaciya, $names"
            }
        }
        return if (names.length > 2) names.trim().substring(0, names.length - 2) else ""
    }
}
