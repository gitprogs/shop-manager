package com.network.shopmanager.data.models

data class Billing(
    var id: String = "",
    var idShop: String = "",
    var idSeller: String = "",
    var sum: Double = 0.0,
    var dollar: Double = 0.0,
    var date: Long = 0,
)
