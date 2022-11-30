package com.network.shopmanager.domain.retrofit

import com.network.shopmanager.data.models.Currency
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
        @GET("oz/arkhiv-kursov-valyut/json/USD/")
    fun getCurrencies():Call<List<Currency>>
}