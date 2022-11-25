package com.network.shopmanager.ui.admin.magazine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.network.shopmanager.data.models.Shop
import com.network.shopmanager.databinding.FragmentMagazineBinding
import com.network.shopmanager.ui.FragmentBase
import com.network.shopmanager.utils.Objects
import com.network.shopmanager.utils.Objects.APP
import com.network.shopmanager.utils.Status
import com.network.shopmanager.utils.toToast
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

@DelicateCoroutinesApi
class MagazineFragment : FragmentBase() {
    private var _binding: FragmentMagazineBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: AddMagazineDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMagazineBinding.inflate(inflater, container, false)
            .apply {
                _binding = this
            }.root
    }

    override fun onStart() {
        super.onStart()
        dialog = AddMagazineDialog()
        binding.btnAddMagazine.setOnClickListener {
            openDialog()
        }
    }


    private fun openDialog() {
        dialog.show(parentFragmentManager, "dialog")
        dialog.onAddButtonClickListener { name, address ->
            with(APP) {
                if (name.isNotEmpty() && address.isNotEmpty()) {
                    val userFirebase = Objects.AUTH.currentUser
                    val id = userFirebase?.uid ?: UUID.randomUUID().toString()
                    val m = Shop(id = id, name = name, address = address)
                    vm.addMagazine(m)
                    dialog.dismiss()
                    vm.addMagazine.observe(viewLifecycleOwner) {
                        when (it.status) {
                            Status.SUCCESS -> it.message.toToast()
                            Status.ERROR -> it.message.toToast()
                            else -> {}
                        }
                    }
                } else {
                    "Do'kon malumotlari to'liq emas".toToast()
                }
            }
        }
    }

}