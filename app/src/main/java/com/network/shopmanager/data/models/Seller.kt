package com.network.shopmanager.data.models

data class Seller(
    var id: String = "",
    var deviceId: String = "",
    var shopId: String = "",
    var login: String = "",
    var password: String = "",
    var passwordScreen: String = "",
    var name: String = "",
    var surname: String = "",
    var address: String = "",
    var geo: String = "",
    var photo: String = "",
    var phone1: String = "",
    var phone2: String = "",
    var salary: Double = 0.0,
    var isAdmin: Boolean = false,
    var permission: Boolean = false,
    var sum: Double = 0.0,
    var dollar: Double = 0.0,
    var lastDate: Long = 0,
    var addedDate: Long = 0
)