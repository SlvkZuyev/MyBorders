package com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.FragmentAllCountriesBinding
import com.overtimedevs.bordersproject.presentation.main_activity.MainActivity
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesRVAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.OnClickListener
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel

class AllCountriesFragment(): Fragment() {
    private val viewModel: AllCountriesViewModel by lazy {
        val application = activity?.application
        val app = application as CountryApp
        val viewModelProviderFactory =
            AllCountriesViewModelProviderFactory(
                app
            )
        ViewModelProvider(
            this,
            viewModelProviderFactory
        )[AllCountriesViewModel::class.java]
    }

    private lateinit var binding: FragmentAllCountriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_countries, container, false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.allCountriesRv.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.lifecycleOwner = this

        val rvAdapter = CountriesRVAdapter()
        rvAdapter.setOnClickLister(object: OnClickListener{

            override fun onCardClick(countryCardItemViewModel: CountryCardItemViewModel) {
                (activity as MainActivity).showCountryInfo(countryCardItemViewModel.countryId)
            }

            override fun onStarClick(countryCardItemViewModel: CountryCardItemViewModel) {
            }

        })
        binding.allCountriesRv.adapter = rvAdapter
        viewModel.loadAllCountries()
    }

    fun isVisible(value: Boolean){
        viewModel.canDisplayChanges = !value
    }

    fun notifySettingsChanged() {
        viewModel.notifySettingsChanged()
    }

    fun applyFilter(filter: String){
        val rvFilter = (binding.allCountriesRv.adapter as CountriesRVAdapter).getFilter()
        rvFilter.filter(filter)
    }

}
