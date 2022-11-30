package com.network.shopmanager.utils

import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.network.shopmanager.databinding.PopupEditDeleteBinding
import com.network.shopmanager.utils.Objects.APP
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class PopupEditDelete {
    fun show(v: View, function: (Int) -> Unit) {
        val viewPop = PopupEditDeleteBinding.inflate(APP.layoutInflater)
        val popup = PopupWindow(
            viewPop.root, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )
        popup!!.isOutsideTouchable = true
        popup!!.elevation = 20.0F
//       val slideIn = Slide()
//        slideIn.slideEdge = Gravity.END
//       popup!!.enterTransition = slideIn
//        val slideOut = Slide()
//        slideOut.slideEdge = Gravity.END
//       popup?.exitTransition = slideOut

        viewPop.tvEdit.setOnClickListener {
            function(it.id)
            popup?.dismiss()
        }
        viewPop.tvDelete.setOnClickListener {
            function(it.id)
            popup?.dismiss()
        }
        TransitionManager.beginDelayedTransition(viewPop.root)
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        popup?.showAtLocation(
            v, Gravity.NO_GRAVITY, // root, Gravity.NO_GRAVITY,
            location[0],// + v.measuredWidth/2,
            location[1] + v.measuredHeight / 2
        )
    }
}