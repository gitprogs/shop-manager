package com.network.shopmanager.data.models

data class Taxi(
    var id: String = "",
    var name: String = "",
    var surname: String = "",
    var address: String = "",
    var geo: String = "",
    var photo: String = "",
    var phone1: String = "",
    var phone2: String = "",
    var car: String = "",
    var cargo: String = "",
    var lastDate: Long = 0,
    var addedDate: Long = 0
)
