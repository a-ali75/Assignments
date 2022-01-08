package arju.ali.assignments

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.widget.TextView


class Assignment1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment1)

        val isNightModeOn = intent.getBooleanExtra("NightMode", false)
        if(isNightModeOn)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //autoCompleteTextView.setOnI

    }

    override fun onResume()
    {
        super.onResume()
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val languages = resources.getStringArray(R.array.languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_items, languages)
        autoCompleteTextView.setAdapter(arrayAdapter)


        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }

        val lang = locale.language
        //Toast.makeText(this,"On Resume - "+lang, Toast.LENGTH_LONG).show()
        val languageList = listOf<String>("en","hi","fr","de")
        if(languageList.contains(lang))
        {
            autoCompleteTextView.setText(resources.getStringArray(R.array.languages).get(languageList.indexOf(lang)), false)
            setLocale(lang)
        }
        else
        {
            autoCompleteTextView.setText(resources.getStringArray(R.array.languages).get(0), false)
            setLocale(languageList.get(0))
            Toast.makeText(this,resources.getString(R.string.toast_lang_not_supported), Toast.LENGTH_LONG).show()
        }

        autoCompleteTextView.setOnItemClickListener { _, _, i, _ ->
            setLocale(languageList.get(i))
        }
    }

    fun setLocale(languageCode: String)
    {
        val appSettingsUIMode: SharedPreferences = getSharedPreferences("AppSettingsUIMode",0)
        val appSettingsEditor: SharedPreferences.Editor = appSettingsUIMode.edit()

        //Toast.makeText(this,"Hello "+appSettingsUIMode.getInt("NightMode",3).toString(),Toast.LENGTH_SHORT).show()
        appSettingsEditor.putString("Locale",languageCode)
        appSettingsEditor.apply()
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
        findViewById<TextView>(R.id.tv1_assignment1).text = resources.getString(R.string.tv1_assignment1)
        findViewById<TextView>(R.id.tv2_assignment1).text = resources.getString(R.string.tv2_assignment1)
        findViewById<TextView>(R.id.tv3_assignment1).text = resources.getString(R.string.tv3_assignment1)
        findViewById<TextView>(R.id.tv4_assignment1).text = resources.getString(R.string.tv4_assignment1)
        findViewById<TextView>(R.id.tv5_assignment1).text = resources.getString(R.string.tv5_assignment1)
        findViewById<TextView>(R.id.tvHeader_assignment1).text = resources.getString(R.string.tvHeader_assignment1)
        findViewById<TextInputLayout>(R.id.textInputLayout_Spinner).hint = resources.getString(R.string.language_hint)
    }
}