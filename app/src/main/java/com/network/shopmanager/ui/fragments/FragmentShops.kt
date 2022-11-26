package com.network.shopmanager.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.network.shopmanager.databinding.DialogAddEditShopBinding
import com.network.shopmanager.databinding.DialogTakePhotoBinding
import com.network.shopmanager.databinding.FragmentShopsBinding
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FragmentShops : FragmentBase() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentShopsBinding? = null
    private val binding get() = _binding!!
    private var viewAddEditShop: DialogAddEditShopBinding? = null
    private var viewAddImage: DialogTakePhotoBinding? = null
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
            viewAddEditShop = DialogAddEditShopBinding.inflate(layoutInflater)
            val dialog = AlertDialog.Builder(requireContext()).setView(viewAddEditShop?.root)
            dialog.setCancelable(true)
            val alert = dialog.create()
            alert.show()

            viewAddEditShop?.ivShop?.setOnClickListener {
                viewAddImage = DialogTakePhotoBinding.inflate(layoutInflater)
                val dialogTakeImage =
                    AlertDialog.Builder(requireContext()).setView(viewAddImage?.root)
                dialogTakeImage.setCancelable(true)
                val alertImage = dialogTakeImage.create()
                alertImage.show()
                alertImage.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                viewAddImage?.tvCancel?.setOnClickListener {
                    alertImage.dismiss()
                }
                viewAddImage?.camera?.setOnClickListener {
                    alertImage.dismiss()
                    APP.requestPermissions(Manifest.permission.CAMERA) {
                        loadImageFromCamera(viewAddEditShop?.ivShop)
                    }
                }
                viewAddImage?.gallery?.setOnClickListener {
                    alertImage.dismiss()
                    APP.requestPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) {
                        loadImageFromGallery(viewAddEditShop?.ivShop)
                    }
                }

            }
            viewAddEditShop?.etGeo?.setOnClickListener {

                APP.requestPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    APP.vm.getCurrentLocation()
                    APP.vm.resultLocation.observe(viewLifecycleOwner) {
                        it.data?.let {
                            viewAddEditShop?.etGeo?.setText("kenglik: ${it?.latitude}\nuzunlik: ${it?.longitude}")
                        }
                    }
                }
            }
            viewAddEditShop?.btnConfirm?.setOnClickListener {
                alert.dismiss()
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