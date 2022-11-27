package com.network.shopmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shops")
data class Shop(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var geo: String = "",
    var photo: String = "",
    var date: Long = 0,
)
