package dev.redfox.powerplay.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPrefs(context: Context) {

    private val PREFS_NAME =Constants.PREFERENCES_NAME
    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    //Boolean Prefs
    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    //String Prefs
    fun putString(key: String?, value: String?) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    //Object Prefs
    fun putObject(key: String?, obj: Any?) {
        val gson = Gson()
        putString(key, gson.toJson(obj))
    }

    fun getObject(key: String?, classOfT: Class<*>?): Any? {
        val json = getString(key!!)
        return Gson().fromJson(json, classOfT)
    }

    fun clear() {
        editor.clear()
            .apply()
    }

}