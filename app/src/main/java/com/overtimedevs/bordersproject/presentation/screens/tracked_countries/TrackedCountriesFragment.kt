package com.overtimedevs.bordersproject.presentation.screens.tracked_countries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.FragmentTrackedCountriesBinding
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.presentation.screens.main_activity.MainActivity
import com.overtimedevs.bordersproject.presentation.adapters.OnClickListener
import com.overtimedevs.bordersproject.presentation.adapters.CountriesRVAdapter
import com.overtimedevs.bordersproject.presentation.model.CountryCardModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackedCountriesFragment(): Fragment() {

    private val viewModel by viewModels<TrackedCountriesViewModel>()

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
        rvAdapter.setOnClickLister(object: OnClickListener {
            override fun onCardClick(countryCardModel: CountryCardModel) {
                (activity as MainActivity).showCountryInfo(countryCardModel.countryId)
            }

            override fun onStarClick(countryCardModel: CountryCardModel) {
                rvAdapter.removeCountry(countryCardModel)
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

        binding.trackedCountriesRv.scrollToPosition(0)

        Log.d("TrackedCountries", "New filter applied: $filter")
    }

    fun setNested(value: Boolean){
        binding.trackedCountriesRv.isNestedScrollingEnabled = value
    }

    private fun onCountriesLoaded(countries: List<CountryCardModel>){
        if(countries.isEmpty()){
            binding.noTrackedCountriesLoadedSign.visibility = View.VISIBLE
        } else {
            binding.noTrackedCountriesLoadedSign.visibility = View.GONE
        }
    }
}
