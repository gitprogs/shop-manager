package com.network.shopmanager.ui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.network.shopmanager.R
import com.network.shopmanager.databinding.FragmentTokenBinding
import com.network.shopmanager.utils.MyProgressBar
import com.network.shopmanager.utils.Objects.APP
import com.network.shopmanager.utils.Objects.NETWORK
import com.network.shopmanager.utils.Status
import com.network.shopmanager.utils.toToast
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FragmentToken : FragmentBase() {

    private var _binding: FragmentTokenBinding? = null
    private val binding get() = _binding!!
    private var tokenEntered = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTokenBinding.inflate(inflater, container, false)
        val progress = MyProgressBar()
        binding.btnToken.setOnClickListener {
            tokenEntered = binding.etToken.text.toString().trim()
            if (tokenEntered.isEmpty()) {
                "Token kiritilmadi".toToast()
                return@setOnClickListener
            } else {
                if (NETWORK.isNetworkConnected()) {
                    APP.vm.fetchToken()

                    APP.vm.resultToken.observe(viewLifecycleOwner) {
                        val message = it.message
                        if (it.status == Status.LOADING) {
                            progress.startProgress()
                        } else if (it.status == Status.ERROR) {
                            progress.stopBar()
                            message.toToast()
                        } else if (it.status == Status.SUCCESS) {
                            progress.stopBar()
                            val token = it.data?.token
                            val tokenFake = "12121212"
                            //   if (token == tokenEntered) {
                            if (tokenEntered == token) {
                                binding.etToken.setText("")
                                findNavController().popBackStack(R.id.fragmentToken, true)
                                findNavController().navigate(R.id.fragmentSignUp)
                            } else {
                                "Token noto'g'ri kiritildi".toToast()
                            }
                        } else {
                            progress.stopBar()
                        }
                    }

                } else {
                    "Internet yo'q".toToast()
                }
            }
        }

        return binding.root
    }

    private fun startTimer(progress: MyProgressBar) {
        val timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progress.setText("Wait...\n${millisUntilFinished / 1000}")
            }

            override fun onFinish() {
                progress.stopBar()
            }
        }
        timer.start()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}