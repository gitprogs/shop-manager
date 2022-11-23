package com.network.shopmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jaredrummler.android.device.DeviceName
import com.network.shopmanager.R
import com.network.shopmanager.data.models.Seller
import com.network.shopmanager.databinding.FragmentSignUpBinding
import com.network.shopmanager.utils.*
import com.network.shopmanager.utils.Objects.APP
import com.network.shopmanager.utils.Objects.NETWORK
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
class FragmentSignUp : FragmentBase() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.btnSignup.setOnClickListener {
            val pass1 = binding.etPassword1.text.toString().trim()
            val pass2 = binding.etPassword2.text.toString().trim()
            val login = binding.etLogin.text.toString().trim().validateEmail()
            val password = pass1
            val name = binding.etName.text.toString().trim()
            val surname = binding.etSurname.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val phone1 = binding.etPhone1.text.toString().trim()
            val phone2 = binding.etPhone2.text.toString().trim()
            if (name.isEmpty()) {
                "Ism kiritilmadi".toToast()
                return@setOnClickListener
            } else if (address.isEmpty()) {
                "Manzil kiritilmadi".toToast()
                return@setOnClickListener
            } else if (phone1.isEmpty()) {
                "Tel raqam kiritilmadi".toToast()
                return@setOnClickListener
            } else if (login.isEmpty()) {
                "Login kiritilmadi".toToast()
                return@setOnClickListener
            } else if (pass1.isEmpty()) {
                "Parol kiritilmadi".toToast()
                return@setOnClickListener
            } else if (pass2.isEmpty()) {
                "Parol qayta kiritilmadi".toToast()
                return@setOnClickListener
            } else if (pass1 != pass2) {
                "Parollar mos kelmadi".toToast()
                return@setOnClickListener
            } else {
                val progressBar = MyProgressBar()
                with(APP) {
                    vm.resultSignUp.observe(viewLifecycleOwner) {
                        val message = it.message
                        if (it.status == Status.LOADING) {
                            progressBar.setText(message)
                            progressBar.startProgress()
                        } else if (it.status == Status.ERROR) {
                            message.toToast()
                            progressBar.stopBar()
                        } else if (it.status == Status.SUCCESS) {
                            message.toToast()
                            progressBar.stopBar()
                            findNavController().popBackStack(R.id.fragmentSignIn, false)
                        }
                    }
                }

                val seller = Seller(
                    id = UUID.randomUUID().toString(),
                    device = "${getDeviceId()}#${DeviceName.getDeviceName()}",
                    login = login,
                    password = pass1,
                    name = name,
                    surname = surname,
                    address = address,
                    phone1 = phone1,
                    phone2 = phone2,
                    addedDate = System.currentTimeMillis()
                )


                if (NETWORK.isNetworkConnected()) {
                    APP.vm.createSeller(seller)
                } else {
                    noInternetToast()
                }

            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}