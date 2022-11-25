package com.network.shopmanager.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.network.shopmanager.R
import com.network.shopmanager.databinding.FragmentAdminBinding
import com.network.shopmanager.ui.admin.magazine.MagazineFragment
import com.network.shopmanager.ui.fragments.FragmentBase
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

    override fun onStart() {
        super.onStart()

        binding.toolbar.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cardShops.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, MagazineFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}