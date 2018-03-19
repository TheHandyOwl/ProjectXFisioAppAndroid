package com.projectx.fisioapp.app.sharedpreferenceswrapper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.projectx.fisioapp.app.settingsmanager.SettingsManagerInteractor


object SharedPreferencesWrapper: SettingsManagerInteractor.SharedPreferencesWrapperInteractor {

    // Interactor methods
    override fun defaultSharedPreferences(context: Context): SharedPreferences =
            defaultSharedPrefs(context)

    override fun customSharedPreferences(context: Context, filename: String): SharedPreferences =
            customSharedPrefs(context, filename)

    override fun getCustomSharedPreference(context: Context, filename: String, key: String): Any? {
        val prefs = customSharedPrefs(context, filename)
        return prefs!![key]
    }

    override fun setCustomSharedPreference(context: Context, filename: String, key: String, value: Any?) {
        customSharedPreferences(context, filename)[key] = value
    }

    override fun deleteCustomSharedPreference(context: Context, filename: String, key: String) {
        customSharedPreferences(context, filename).edit().remove(key).apply()
    }

    override fun clearCustomFileSharedPreferences(context: Context, filename: String) {
        customSharedPreferences(context, filename).edit().clear().apply()
    }

    // Library

    fun defaultSharedPrefs(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customSharedPrefs(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit({ it.putString(key, value) })
            is Int -> edit({ it.putInt(key, value) })
            is Boolean -> edit({ it.putBoolean(key, value) })
            is Float -> edit({ it.putFloat(key, value) })
            is Long -> edit({ it.putLong(key, value) })
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

}
