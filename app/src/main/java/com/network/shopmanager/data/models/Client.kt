package com.network.shopmanager.data.models

data class Client(
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var address: String = "",
    var geo: String = "",
    var photo: String = "",
    var phone1: String = "",
    var phone2: String = "",
    var sum: Double = 0.0,
    var dollar: Double = 0.0,
    var voiceNeed: Boolean = false,
    var lastDate: Long = 0,
    var addedDate: Long = 0
)
