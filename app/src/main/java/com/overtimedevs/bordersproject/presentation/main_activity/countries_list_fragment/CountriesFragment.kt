package com.overtimedevs.bordersproject.presentation.main_activity.countries_list_fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.FragmentCountriesBinding

class CountriesFragment(var showTrackedOnly: Boolean) : Fragment() {

    private val viewModel: CountriesViewModel by lazy {
        val application = activity?.application
        val app = application as CountryApp
        val viewModelProviderFactory =
            CountriesViewModelProviderFactory(
                app
            )
        ViewModelProvider(
            this,
            viewModelProviderFactory
        )[CountriesViewModel::class.java]
    }

    private lateinit var binding: FragmentCountriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_countries, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("SlvkLog", "Created with $showTrackedOnly")

        binding.viewModel = viewModel
        binding.countriesRv.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.lifecycleOwner = this

        viewModel.loadCountries(showTrackedOnly)
    }

}