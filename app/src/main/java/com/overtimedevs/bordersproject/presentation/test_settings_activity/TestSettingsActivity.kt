package com.overtimedevs.bordersproject.presentation.test_settings_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ActivityTestSettingsBinding
import java.util.*
import kotlin.collections.ArrayList

class TestSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTestSettingsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_test_settings)


        val items = getCountries()
        val adapter = ArrayAdapter(this, R.layout.item_list_country, items)
        adapter.filter.filter(null)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }


    fun getCountries() : List<String>{
        val locales: Array<Locale> = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()

        Log.d("SlvkLog", "${countries.toString()}")
        return countries
    }

}