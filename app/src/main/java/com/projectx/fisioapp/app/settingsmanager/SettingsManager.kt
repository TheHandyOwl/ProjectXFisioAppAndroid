package com.projectx.fisioapp.app.settingsmanager

import android.content.Context
import android.content.SharedPreferences
import com.projectx.fisioapp.app.sharedpreferenceswrapper.SharedPreferencesWrapper
import com.projectx.fisioapp.app.sharedpreferenceswrapper.SharedPreferencesWrapper.get
import com.projectx.fisioapp.app.sharedpreferenceswrapper.SharedPreferencesWrapper.set

class SettingsManager {
    var sharedPreferencesWrapper: SharedPreferencesWrapper

    init {
        sharedPreferencesWrapper = SharedPreferencesWrapper
    }

    // Fields
    val KEY_UID = "UID"
    val KEY_TOKEN = "TOKEN"

    // Files
    val FILE_USER_PREFERENCES = "USER_PREFERENCES"

    val ALL_KEYS_PREFERENCES = arrayOf(
            KEY_UID,
            KEY_TOKEN
    )

    val ALL_FILES_PREFERENCES = arrayOf(
            FILE_USER_PREFERENCES
    )

    fun defaultSharedPreferences(context: Context): SharedPreferences =
            sharedPreferencesWrapper.defaultSharedPrefs(context)

    fun customSharedPreferences(context: Context, filename: String): SharedPreferences =
            sharedPreferencesWrapper.customSharedPrefs(context, filename)

    inline fun <reified T : Any> getCustomSharedPreference(context: Context, filename: String, key: String): T? {
        var prefs = customSharedPreferences(
                context,
                filename)
        return prefs[key]
    }

    fun setCustomSharedPreference(context: Context, filename: String, key: String, value: Any?) {
        customSharedPreferences(context, filename)[key] = value
    }

    fun deleteCustomSharedPreference(context: Context, filename: String, key: String) {
        customSharedPreferences(context, filename).edit().remove(key).apply()
    }

    fun clearCustomFileSharedPreferences(context: Context, filename: String) {
        customSharedPreferences(context, filename).edit().clear().apply()
    }

}
