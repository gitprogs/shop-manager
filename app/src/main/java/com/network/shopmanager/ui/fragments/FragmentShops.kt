package com.network.shopmanager.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.network.shopmanager.R
import com.network.shopmanager.data.models.Shop
import com.network.shopmanager.databinding.DialogAddEditShopBinding
import com.network.shopmanager.databinding.DialogTakePhotoBinding
import com.network.shopmanager.databinding.FragmentShopsBinding
import com.network.shopmanager.ui.adapters.AdapterShops
import com.network.shopmanager.utils.*
import com.network.shopmanager.utils.Objects.APP
import com.network.shopmanager.utils.Objects.DB_LOCAL
import com.network.shopmanager.utils.Objects.NETWORK
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

@DelicateCoroutinesApi
class FragmentShops : FragmentBase() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentShopsBinding? = null
    private val binding get() = _binding!!
    private var viewAddEditShop: DialogAddEditShopBinding? = null
    private var viewAddImage: DialogTakePhotoBinding? = null
    private var adapter: AdapterShops? = null
    private var geo = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
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
        adapter = AdapterShops(object : AdapterShops.ShopClickListener {
            override fun onClick(shop: Shop, position: Int, v: View) {
                PopupEditDelete().show(v) {
                    when (it) {
                        R.id.tv_edit -> showDialog(shop)
                        R.id.tv_delete -> deleteShop(shop)
                    }
                }
            }
        })
        binding.rvShops.adapter = adapter
        binding.rvShops.addItemDecoration(
            DividerItemDecoration(
                APP,
                LinearLayoutManager.VERTICAL
            )
        )

        DB_LOCAL.daoShop().getShops()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                (Consumer {
                    adapter?.submitList(it)
                }),

                (Consumer {

                }),
                { }
            )

    }

    private fun deleteShop(shop: Shop) {
        ShowAlertDialog(
            title = "Diqqat !",
            message = "${shop.address} manzilidagi ${shop.name} do'konni o'chirishga ishonchingiz komilmi ?"
        ) {
            if (it) {
                if (NETWORK.isNetworkConnected()) {
                    APP.vm.deleteShop(shop)
                } else {
                    noInternetToast()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDialog(editShop: Shop? = null) {
        viewAddEditShop = DialogAddEditShopBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(viewAddEditShop?.root)
        dialog.setCancelable(true)
        val alert = dialog.create()
        alert.show()

        if (editShop != null) {
            viewAddEditShop?.title?.text = "O'zgartirish"
            viewAddEditShop?.etShopName?.setText(editShop.name)
            viewAddEditShop?.etShopAddress?.setText(editShop.address)
            try {
                Picasso.get().load(editShop.photo)
                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.shop)
                    .into(viewAddEditShop?.ivShop)
            } catch (e: Exception) {
            }
            try {
                val geoArray = editShop.geo.split("#")
                viewAddEditShop?.etGeo?.text = "${geoArray[0]}\n${geoArray[1]}"
            } catch (e: Exception) {
            }
        }

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
            val locationManager = APP.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Gps().openGpsSettings()
            } else {
                APP.requestPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    APP.vm.getCurrentLocation()
                    APP.vm.resultLocation.observe(viewLifecycleOwner) {
                        it.data?.let {
                            geo = "${it.latitude}#${it.longitude}"
                            viewAddEditShop?.etGeo?.setText("kenglik: ${it?.latitude}\nuzunlik: ${it?.longitude}")
                        }
                    }
                }
            }
        }
        viewAddEditShop?.btnConfirm?.setOnClickListener {
            saveShop(editShop)
            alert.dismiss()
        }
    }

    private fun saveShop(editShop: Shop? = null) {
        val shopName = viewAddEditShop?.etShopName?.text?.toString()?.trim()
        val shopAddress = viewAddEditShop?.etShopAddress?.text?.toString()?.trim()
        if (shopName == null || shopName.isEmpty()) {
            "Do'kon nomi kiritilmadi".toToast()
        } else if (shopAddress == null || shopAddress.isEmpty()) {
            "Do'kon manzili kiritilmadi".toToast()
        } else {
            val shop = if (editShop != null) editShop else Shop(
                id = UUID.randomUUID().toString()
            )
            shop.name = shopName
            shop.address = shopAddress
            shop.geo = this.geo
            shop.date = System.currentTimeMillis()

            if (NETWORK.isNetworkConnected()) {
                APP.vm.addMagazine(shop, imageBytes)
                imageBytes = null
                APP.vm.addMagazine.observe(viewLifecycleOwner) {
                    if (it.status == Status.SUCCESS) {
                        it.message.toToast()
                        binding.rvShops.scrollToPosition(adapter?.itemCount ?: 0)
                    }
                }
            } else {
                noInternetToast()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}