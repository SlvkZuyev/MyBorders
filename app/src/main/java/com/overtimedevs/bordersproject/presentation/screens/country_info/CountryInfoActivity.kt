package com.overtimedevs.bordersproject.presentation.screens.country_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ActivityCountryInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryInfoActivity : AppCompatActivity() {

    private val viewModel by viewModels<CountryInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCountryInfoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_country_info)
        val countryId : Int = intent.getIntExtra("country_id", -1)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loadCountry(countryId)
    }
}