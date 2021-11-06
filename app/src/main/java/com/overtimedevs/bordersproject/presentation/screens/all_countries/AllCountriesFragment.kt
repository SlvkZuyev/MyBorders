package com.overtimedevs.bordersproject.presentation.screens.all_countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.FragmentAllCountriesBinding
import com.overtimedevs.bordersproject.presentation.screens.main_activity.MainActivity
import com.overtimedevs.bordersproject.presentation.adapters.CountriesRVAdapter
import com.overtimedevs.bordersproject.presentation.adapters.OnClickListener
import com.overtimedevs.bordersproject.presentation.model.CountryCardModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCountriesFragment(): Fragment() {

    private val viewModel by viewModels<AllCountriesViewModel>()
    private lateinit var shimmerViewContainer: ShimmerFrameLayout
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
        setupShimmer()

        binding.viewModel = viewModel
        binding.allCountriesRv.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.lifecycleOwner = this

        viewModel.onCountriesStartLoading = { onCountriesStartLoading() }
        viewModel.onCountriesLoaded = {onCountriesLoaded(it)}
        setupRecyclerView()

        viewModel.loadAllCountries(forceShowChanges = true)

        if(viewModel.settingsNotApplied()){
            showSettingsSign()
        }
    }

    private fun setupShimmer(){
        shimmerViewContainer = binding.shimmerViewContainer
    }


    private fun showSettingsSign(){
        binding.noCountriesLoadedSign.visibility = View.VISIBLE
    }

    private fun hideSettingsSign(){
        binding.noCountriesLoadedSign.visibility = View.GONE
    }

    private fun setupRecyclerView(){
        val rvAdapter = CountriesRVAdapter()
        rvAdapter.setOnClickLister(object: OnClickListener {

            override fun onCardClick(countryCardModel: CountryCardModel) {
                (activity as MainActivity).showCountryInfo(countryCardModel.countryId)
            }

            override fun onStarClick(countryCardModel: CountryCardModel) {
            }

        })
        binding.allCountriesRv.adapter = rvAdapter
    }

    fun isVisible(value: Boolean){
        viewModel.canDisplayChanges = !value
    }

    fun notifySettingsChanged() {
        viewModel.notifySettingsChanged()
        hideSettingsSign()
    }

    fun applyFilter(filter: String){
        val rvFilter = (binding.allCountriesRv.adapter as CountriesRVAdapter).getFilter()
        rvFilter.filter(filter)
        binding.allCountriesRv.scrollToPosition(0)
    }

    fun setNested(value: Boolean){
        binding.allCountriesRv.isNestedScrollingEnabled = value
    }

    private fun onCountriesStartLoading(){
        binding.allCountriesRv.visibility = View.INVISIBLE
        shimmerViewContainer.visibility = View.VISIBLE
        shimmerViewContainer.startShimmerAnimation()
    }

    private fun onCountriesLoaded(countries: List<CountryCardModel>){
        shimmerViewContainer.visibility = View.GONE
        shimmerViewContainer.stopShimmerAnimation()
        binding.allCountriesRv.visibility = View.VISIBLE

        if(countries.isEmpty()){
            //binding.noCountriesLoadedSign.visibility = View.VISIBLE
        } else {
            hideSettingsSign()
        }
    }
}
