package com.overtimedevs.bordersproject.presentation.main_activity.fragments.tracked_countries_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.FragmentTrackedCountriesBinding
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.presentation.main_activity.MainActivity
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.OnClickListener
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesRVAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment.AllCountriesViewModel
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackedCountriesFragment(): Fragment() {

    private val viewModel by viewModels<TrackedCountriesViewModel>()
    /*
    private val viewModel: TrackedCountriesViewModel by lazy {
        val application = activity?.application
        val app = application as CountryApp
        val viewModelProviderFactory =
            TrackedCountriesViewModelProviderFactory(
                app
            )
        ViewModelProvider(
            this,
            viewModelProviderFactory
        )[TrackedCountriesViewModel::class.java]
    }

     */

    private lateinit var binding: FragmentTrackedCountriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tracked_countries, container, false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.trackedCountriesRv.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.lifecycleOwner = this
        viewModel.onCountriesLoaded = {onCountriesLoaded(it)}
        val rvAdapter = CountriesRVAdapter()
        rvAdapter.setOnClickLister(object: OnClickListener{
            override fun onCardClick(countryCardItemViewModel: CountryCardItemViewModel) {
                (activity as MainActivity).showCountryInfo(countryCardItemViewModel.countryId)
            }

            override fun onStarClick(countryCardItemViewModel: CountryCardItemViewModel) {
                rvAdapter.removeCountry(countryCardItemViewModel)
            }
        })

        binding.trackedCountriesRv.adapter = rvAdapter

        viewModel.loadTrackedCountries()
    }

    fun isVisible(value: Boolean){
        viewModel.canShowChanges = !value
    }

    fun notifySettingsChanged() {
        viewModel.loadTrackedCountries()
    }

    fun applyFilter(filter: String){
        val rvFilter = (binding.trackedCountriesRv.adapter as CountriesRVAdapter).getFilter()
        rvFilter.filter(filter)

        Log.d("TrackedCountries", "New filter applied: ${filter}")
    }

    fun setNested(value: Boolean){
        binding.trackedCountriesRv.isNestedScrollingEnabled = value
    }

    private fun onCountriesLoaded(countries: List<Country>){
        if(countries.isEmpty()){
            binding.noTrackedCountriesLoadedSign.visibility = View.VISIBLE
        } else {
            binding.noTrackedCountriesLoadedSign.visibility = View.GONE
        }
    }
}
