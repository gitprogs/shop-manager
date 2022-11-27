package com.network.shopmanager.utils

import android.util.Log
import com.crushtech.task_7_7_workmanager.retrofit.ApiClient
import com.network.shopmanager.data.models.Currency
import com.network.shopmanager.domain.retrofit.ApiService
import com.network.shopmanager.utils.Constants.KEY_CURRENCY_RATE
import com.network.shopmanager.utils.Objects.NETWORK
import com.network.shopmanager.utils.Objects.PREF
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRate {
    private val defaultRate = "11230"  // 27.11.2022
    fun getRate(function: (String) -> Unit) {
        if (NETWORK.isNetworkConnected()) {
            ApiClient.getRetrofit().create(ApiService::class.java)
                .getCurrencies()
                .enqueue(object :
                    Callback<List<Currency>> {
                    override fun onResponse(
                        call: Call<List<Currency>>,
                        response: Response<List<Currency>>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val listApi = response.body()!!
                            if (!listApi.isEmpty()) {
                                val currency = listApi[0]
                                val rate = currency.Rate.substringBeforeLast(".")
                                PREF.setString(KEY_CURRENCY_RATE, rate)
                                function(rate)
                            }
                        } else {
                            returnDefault(function)
                        }
                    }

                    override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                        Log.d("retrofit", "${t.message}")
                        returnDefault(function)
                    }
                })
        } else {
            returnDefault(function)
        }

    }

    private fun returnDefault(function: (String) -> Unit) {
        var rate = PREF.getString(KEY_CURRENCY_RATE)
        if (rate == null || rate.trim().isEmpty()) {
            rate = defaultRate
        }
        function(rate)
    }
}