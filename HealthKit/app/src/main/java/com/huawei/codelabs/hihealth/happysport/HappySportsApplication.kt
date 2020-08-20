package com.huawei.codelabs.hihealth.happysport

import android.app.Application
import android.content.Context
import java.util.*


/**
 * @since 2020-08-19
 */
class HappySportsApplication : Application() {
    private val FILE_NAME = "data"

    companion object {
        val LANGUAGE_KEY = "language"
        var instance: HappySportsApplication? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate();
        instance = this

        val curLocale = resources.configuration.locale
        val locale = if (curLocale.language == Locale.SIMPLIFIED_CHINESE.language) curLocale else Locale.ENGLISH
        Locale.setDefault(locale)
        val config = resources.configuration
        val metrics = resources.displayMetrics
        config.locale = locale
        resources.updateConfiguration(config, metrics)
        setLanguage(this, LANGUAGE_KEY, locale.language);
    }

    fun setLocale(context: Context, locale: Locale) {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        val metrics = context.resources.displayMetrics
        config.locale = locale
        context.resources.updateConfiguration(config, metrics)
    }

    fun setLanguage(context: Context, key: String, `object`: Any) {
        val editor = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        ).edit()

        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }
        editor.apply()
    }

    fun getLanguage(context: Context, key: String, defaultValue: Any): Any? {
        val sp = context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )

        if (defaultValue is String) {
            return sp.getString(key, defaultValue)
        } else if (defaultValue is Int) {
            return sp.getInt(key, defaultValue)
        } else if (defaultValue is Boolean) {
            return sp.getBoolean(key, defaultValue)
        } else if (defaultValue is Float) {
            return sp.getFloat(key, defaultValue)
        } else if (defaultValue is Long) {
            return sp.getLong(key, defaultValue)
        }

        return null
    }
}