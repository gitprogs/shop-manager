package com.network.shopmanager.data.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


private const val SHARED_PREFS_NAME = "shared_prefs_shopmanager"

class Pref(context: Context) {
    private val gson = Gson()
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun delete(key: String){
        editor.remove(key+"int").apply()
        editor.remove(key+"boolean").apply()
        editor.remove(key+"long").apply()
        editor.remove(key+"string").apply()
    }
    fun setInt(key: String, value: Int) {
        editor.putInt(key+"int", value).apply()
    }
    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key+"int", 0)
    }
    fun setLong(key: String, value: Long) {
        editor.putLong(key+"long", value).apply()
    }
    fun getLong(key: String): Long? {
        return sharedPreferences.getLong(key+"long", 0)
    }
    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key+"boolean", value).apply()
    }
    fun getBoolean(key: String): Boolean? {
        return sharedPreferences.getBoolean(key+"boolean", false)
    }
    fun setString(key: String, value: String) {
        editor.putString(key+"string", value).apply()
    }
    fun getString(key: String): String? {
        return sharedPreferences.getString(key+"string", "")
    }

//    override fun saveUser(user: User): Boolean {
//        val toJson = gson.toJson(user)
//        sharedPreferences.edit().putString(KEY_USER, toJson).apply()
//        return true
//    }
//    override fun getUser(): User {
//        val userStr = sharedPreferences.getString(KEY_USER, "") ?: ""
//        return gson.fromJson(userStr, User::class.java)?: User()
//    }
}