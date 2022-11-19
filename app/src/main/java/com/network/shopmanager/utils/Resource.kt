package com.network.shopmanager.utils

data class Resource<out T>(
    val status: Status = Status.ERROR,
    val data: T? = null,
    val message: String = "Kuting..."
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, "")
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, "")
        }

    }
    fun <T> cancel(data: T?): Resource<T> {
        return Resource(Status.RESET, data, "")
    }

}
