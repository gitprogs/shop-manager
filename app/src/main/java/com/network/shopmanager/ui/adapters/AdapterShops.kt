package com.network.shopmanager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.network.shopmanager.data.models.Shop
import com.network.shopmanager.databinding.ItemShopBinding

class AdapterShops(val list: List<Shop>, val listener: ShopClickListener) :
    RecyclerView.Adapter<AdapterShops.Vh>() {
    inner class Vh(val v: ItemShopBinding) : RecyclerView.ViewHolder(v.root) {
        fun onBind(shop: Shop, position: Int) {
            //  v.ivShop.setImageResource(shop.photo)
            v.tvShopName.text = shop.name
            v.ivMore.setOnClickListener {
                listener.onClick(shop, position, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemShopBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    interface ShopClickListener {
        fun onClick(shop: Shop, position: Int, v: View)
    }
}