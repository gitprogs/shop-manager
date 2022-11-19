package com.network.shopmanager.data.models



data class UserDevice(
    var id: String = "",
    val userId: String = "",
    var email: String = "",
    var deviceName: String = "",
    var libVersion: String = "",
    var date: Long = 0
)