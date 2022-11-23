package com.network.shopmanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.network.shopmanager.R
import com.network.shopmanager.databinding.FragmentSignInBinding
import com.network.shopmanager.utils.*
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FragmentSignIn : FragmentBase() {

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val progressBar = MyProgressBar()
        with(APP) {
            vm.resultSignIn.observe(viewLifecycleOwner) {
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
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    finish()
                    startActivity(intent)
                }
            }
        }
        binding.btnSignin.setOnClickListener {
            if (Objects.NETWORK.isNetworkConnected()) {
                val login = binding.etLogin.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                if (login.isEmpty() || password.isEmpty()) {
                    "Ma'lumot to'liq kiritilmadi".toToast()
                } else if (password.length < 6) {
                    "Parol 6 ta simvoldan kam bo'lmasin".toToast()
                } else {
                    binding.etLogin.setText("")
                    binding.etPassword.setText("")
                    APP.vm.signIn(login.validateEmail(), password)
                }
            }
        }
        binding.tvSignup.setOnClickListener {
            findNavController().navigate(R.id.fragmentToken)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}