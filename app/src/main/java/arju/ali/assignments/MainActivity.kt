package arju.ali.assignments

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlin.math.log

class MainActivity : AppCompatActivity()
{
    private lateinit var switch: Switch
    private val defaultValue: Int = 2
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
                appSettingsEditor.putInt("NightMode",1)
                appSettingsEditor.apply()
                switch.text = applicationContext.resources?.getString(R.string.disableDarkMode)
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                appSettingsEditor.putInt("NightMode",0)
                appSettingsEditor.apply()
                switch.text = applicationContext.resources?.getString(R.string.enableDarkMode)
            }
        }
    }

    private fun onClickButtonAssignment1()
    {
        findViewById<Button>(R.id.btnAssignment1).setOnClickListener {
            startActivity(Intent(this, Assignment1::class.java))
        }
    }
}