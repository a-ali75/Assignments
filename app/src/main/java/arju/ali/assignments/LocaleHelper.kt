package arju.ali.assignments

import android.content.Context
import java.util.*
import android.os.Build

import android.annotation.TargetApi
import android.content.res.Configuration
import android.content.res.Resources


class LocaleHelper
{
    // object of inbuilt Locale class and passing language argument to it
    @TargetApi(Build.VERSION_CODES.N)
    fun updateResource(context: Context, language: String): Context
    {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    @SuppressWarnings("deprecation")
    fun updateResourcesLegacy(context: Context, language: String): Context
    {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.getConfiguration()
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return context
    }
}