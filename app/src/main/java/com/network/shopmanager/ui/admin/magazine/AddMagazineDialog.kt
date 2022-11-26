package com.network.shopmanager.ui.admin.magazine

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.network.shopmanager.databinding.AddMagazineDialogBinding


class AddMagazineDialog : DialogFragment() {

    private lateinit var binding: AddMagazineDialogBinding

    private var onAddButtonClickListener: ((String, String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = AddMagazineDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        binding.btnAddMagazine.setOnClickListener {
            val nameMagazine = binding.magazineName.text.toString()
            val addressMagazine = binding.addressMagazine.text.toString()
            onAddButtonClickListener?.invoke(nameMagazine, addressMagazine)
        }

        binding.addLocation.setOnClickListener {
            // TODO.......
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    fun onAddButtonClickListener(listener: ((name: String, address: String) -> Unit)) {
        onAddButtonClickListener = listener
    }

}