package com.network.shopmanager.data.models

data class UserDevice(
    var id: String = "",
    var userId: String = "",
    var email: String = "",
    var deviceName: String = "",
    var lastDate: Long = 0,
    var date: Long = 0
)