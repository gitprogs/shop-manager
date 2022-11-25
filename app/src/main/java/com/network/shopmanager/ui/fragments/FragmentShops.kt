package com.network.shopmanager.ui.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.network.shopmanager.databinding.DialogAddEditShopBinding
import com.network.shopmanager.databinding.FragmentShopsBinding
import com.network.shopmanager.utils.Objects.APP
import com.network.shopmanager.utils.toToast
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FragmentShops : FragmentBase() {
    private val REQUEST_CODE_LOCATION = 1212
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentShopsBinding? = null
    private val binding get() = _binding!!
    private var view: DialogAddEditShopBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.fabAddShop.setOnClickListener {
            showDialog()
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDialog() {

        val locationManager = APP.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        } else {
            view = DialogAddEditShopBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext()).setView(view?.root)
            dialog.setCancelable(true)
            val alert = dialog.create()
            alert.show()
            view?.etGeo?.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        APP,
                        ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            APP,
                            ACCESS_FINE_LOCATION
                        )
                    ) {
                        ActivityCompat.requestPermissions(
                            APP,
                            arrayOf(ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION
                        )
                    } else {
                        ActivityCompat.requestPermissions(
                            APP,
                            arrayOf(ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION
                        )
                    }
                } else {
                    APP.vm.getCurrentLocation()
                    APP.vm.resultLocation.observe(viewLifecycleOwner) {
                        it.data?.let {
                            view?.etGeo?.setText("kenglik: ${it?.latitude}\nuzunlik: ${it?.longitude}")
                        }
                    }

                }
            }
            view?.btnConfirm?.setOnClickListener {
                alert.dismiss()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(APP, ACCESS_FINE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Log.d("loc", "onRequestPermissionsResult")

                        APP.vm.getCurrentLocation()

                    }
                } else {
                    "Ruxsat rad etildi".toToast()
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(APP)
        builder.setMessage("GPS yoqilmagan. Yoqilsinmi?")
            .setCancelable(false)
            .setPositiveButton("Ha") { dialog, id ->
                startActivityForResult(
                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 11
                )
            }
            .setNegativeButton("Yo'q") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()


    }


}