package com.network.shopmanager.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.network.shopmanager.R
import com.network.shopmanager.data.models.Shop
import com.network.shopmanager.databinding.ItemShopBinding
import com.network.shopmanager.utils.Objects.APP
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.File

@DelicateCoroutinesApi
class AdapterShops(val listener: ShopClickListener) :
    ListAdapter<Shop, AdapterShops.Vh>(ShopDiffUtil()) {

    inner class Vh(val v: ItemShopBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(shop: Shop, position: Int) {
            //  v.ivShop.setImageResource(shop.photo)
            val file: File? = APP.getFileStreamPath(shop.id)
            try {
                if (file != null) {
                    Picasso.get()
                        .load(file)
                        .placeholder(R.drawable.shop)
                        .centerCrop()
                        .fit()
                        .into(v.ivShop)
                } else {
                    Picasso.get()
                        .load(shop.photo)
                        .placeholder(R.drawable.shop)
                        .centerCrop()
                        .fit()
                        .into(v.ivShop)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            v.tvShopName.text = shop.name
            v.tvShopAddress.text = shop.address
            v.ivMore.setOnClickListener {
                listener.onClick(shop, position, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemShopBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position)
    }

    interface ShopClickListener {
        fun onClick(shop: Shop, position: Int, v: View)
    }

    class ShopDiffUtil : DiffUtil.ItemCallback<Shop>() {
        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean {
            return oldItem.equals(newItem)
        }
    }
}