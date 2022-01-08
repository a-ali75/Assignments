package arju.ali.assignments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity()
{
    private lateinit var switch: Switch
    private val defaultValue: Int = 2
    private val defaultStringVal: String = "UNKNOWN"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initialize widgets
        switch = findViewById<Switch>(R.id.switchThemeMode)

        val appSettingsUIMode: SharedPreferences = getSharedPreferences("AppSettingsUIMode",0)
        val appSettingsEditor: SharedPreferences.Editor = appSettingsUIMode.edit()
        val isNightModeOn: Int = appSettingsUIMode.getInt("NightMode",defaultValue)

        if(isNightModeOn==defaultValue)
        {
            checkAppThemeMode(appSettingsEditor)
        }
        else if(isNightModeOn==1)
        {
            switch.isChecked = true
            switch.text = applicationContext.resources?.getString(R.string.disableDarkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            switch.isChecked = false
            switch.text = applicationContext.resources?.getString(R.string.enableDarkMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        onClickButtonAssignment1()
        onClickSwitchMode(appSettingsEditor)
    }

    override fun onResume()
    {
        super.onResume()
        checkLocales()
    }

    private fun checkLocales()
    {
        val appSettingsUIMode: SharedPreferences = getSharedPreferences("AppSettingsUIMode",0)

        val localeLang: String = appSettingsUIMode.getString("Locale",defaultStringVal) ?: defaultStringVal

        if(localeLang.equals(defaultStringVal))
        {
            val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                resources.configuration.locales.get(0)
            } else {
                resources.configuration.locale
            }

            val lang = locale.language
            setLocale(lang)
            //Toast.makeText(this,"Main On Resume - "+lang, Toast.LENGTH_SHORT).show()
        }
        else
        {
            setLocale(localeLang)
            //Toast.makeText(this,"Main On Resume - "+localeLang, Toast.LENGTH_SHORT).show()
        }

        //Toast.makeText(this,"Hello "+appSettingsUIMode.getInt("NightMode",3).toString(),Toast.LENGTH_SHORT).show()

    }

    fun setLocale(languageCode: String)
    {
        val context: Context = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleHelper().updateResource(this, languageCode)
        }
        else
        {
            // for devices having lower version of android os
            LocaleHelper().updateResourcesLegacy(this, languageCode)
        }

        val res = context.resources
        updateTextViews(res)

    }
    fun updateTextViews(resources: Resources)
    {
        findViewById<TextView>(R.id.btnAssignment1).text = resources.getString(R.string.btnAssignment1)
        if(switch.isChecked)
        {
            findViewById<Switch>(R.id.switchThemeMode).text = resources.getString(R.string.disableDarkMode)
        }
        else
        {
            findViewById<Switch>(R.id.switchThemeMode).text = resources.getString(R.string.enableDarkMode)
        }

    }

    private fun checkAppThemeMode(appSettingsEditor: SharedPreferences.Editor)
    {
        when (applicationContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK))
        {
            Configuration.UI_MODE_NIGHT_YES -> {
                switch.isChecked = true
                appSettingsEditor.putInt("NightMode",1)
                appSettingsEditor.apply()
                switch.text = applicationContext.resources?.getString(R.string.disableDarkMode)

            }
            Configuration.UI_MODE_NIGHT_NO -> {
                switch.isChecked = false
                appSettingsEditor.putInt("NightMode",0)
                appSettingsEditor.apply()
                switch.text = applicationContext.resources?.getString(R.string.enableDarkMode)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                switch.isChecked = false
                appSettingsEditor.putInt("NightMode",0)
                appSettingsEditor.apply()
                switch.text = applicationContext.resources?.getString(R.string.enableDarkMode)
            }
        }
    }

    private fun onClickSwitchMode(appSettingsEditor: SharedPreferences.Editor)
    {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                checkLocales()
                appSettingsEditor.putInt("NightMode",1)
                appSettingsEditor.apply()
                //switch.text = applicationContext.resources?.getString(R.string.disableDarkMode)

            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                checkLocales()
                appSettingsEditor.putInt("NightMode",0)
                appSettingsEditor.apply()
                //switch.text = applicationContext.resources?.getString(R.string.enableDarkMode)

            }
        }
    }

    private fun onClickButtonAssignment1()
    {
        findViewById<Button>(R.id.btnAssignment1).setOnClickListener {
            startActivity(Intent(this, Assignment1::class.java).putExtra("NightMode",switch.isChecked))
        }
    }
}