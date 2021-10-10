package com.overtimedevs.bordersproject.presentation.country_info_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ActivityCountryInfoBinding
import com.overtimedevs.bordersproject.presentation.main_activity.MainViewModel
import com.overtimedevs.bordersproject.presentation.main_activity.MainViewModelProviderFactory



class CountryInfoActivity : AppCompatActivity() {

    private val viewModel: CountryInfoViewModel by lazy {
        val app = application as CountryApp
        val viewModelProviderFactory =
            CountryInfoViewModelProviderFactory(
                app,
                intent
            )
        ViewModelProvider(
            this,
            viewModelProviderFactory
        )[CountryInfoViewModel::class.java]
    }



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