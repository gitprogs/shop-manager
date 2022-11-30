package com.network.shopmanager.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.network.shopmanager.R
import com.network.shopmanager.databinding.FragmentAdminBinding
import com.network.shopmanager.utils.Objects.DB_LOCAL
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FragmentAdmin : FragmentBase() {

    private var _binding: FragmentAdminBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        val shopsCount=DB_LOCAL.daoShop().getShopsCount()
        binding.tvShops.text="Do'konlar($shopsCount)"
        binding.cardShops.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_admin_to_fragmentShops)
        }
        binding.toolbar.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
