package com.overtimedevs.bordersproject.presentation.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ActivityMainBinding
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        val app = application as CountryApp
        val viewModelProviderFactory =
            MainViewModelProviderFactory(
                app,
                intent
            )
        ViewModelProvider(
            this,
            viewModelProviderFactory
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.countries.observe(this){
            binding.countriesRv.adapter = CountriesAdapter(it)
            binding.countriesRv.layoutManager = LinearLayoutManager(this)
        }
    }
}