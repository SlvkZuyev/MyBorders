package com.overtimedevs.bordersproject.presentation.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.data.repository.CountryRepositoryImpl
import com.overtimedevs.bordersproject.databinding.ActivityMainBinding
import com.overtimedevs.bordersproject.di.AppModule
import com.overtimedevs.bordersproject.domain.use_case.CountryUseCases
import com.overtimedevs.bordersproject.domain.use_case.GetCountries
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesAdapter
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = MainViewModel(application)

        viewModel.countries.observe(this){
            binding.countriesRv.adapter = CountriesAdapter(it)
            binding.countriesRv.layoutManager = LinearLayoutManager(this)
        }
    }
}