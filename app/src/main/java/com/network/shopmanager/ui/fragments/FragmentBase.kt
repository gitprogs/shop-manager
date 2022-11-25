package com.network.shopmanager.ui.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
open class FragmentBase : Fragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
    }
}