package com.network.shopmanager.data.models

data class Message(
    var id: String = "",
    var idClient: String = "",
    var idShop: String = "",
    var idSeller: String = "",
    var idWriter: String = "",
    var text: String = "",
    var image: String = "",
    var audio: String = "",
    var sum: Double = 0.0,
    var dollar: Double = 0.0,
    var seen: Boolean=false,
    var date: Long = 0,
)
