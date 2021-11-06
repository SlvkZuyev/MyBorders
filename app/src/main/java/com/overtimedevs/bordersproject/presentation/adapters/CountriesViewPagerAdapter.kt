package com.overtimedevs.bordersproject.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.overtimedevs.bordersproject.presentation.screens.all_countries.AllCountriesFragment
import com.overtimedevs.bordersproject.presentation.screens.tracked_countries.TrackedCountriesFragment

class CountriesViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var trackedCountriesFragment: TrackedCountriesFragment? = null
    private var allCountriesFragment: AllCountriesFragment? = null
    private var currentPosition = 0

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == POSITION_TRACKED_COUNTRIES) {
            trackedCountriesFragment = TrackedCountriesFragment()
            //trackedCountriesFragment?.isVisible(true)
            trackedCountriesFragment!!
        } else {
            allCountriesFragment = AllCountriesFragment()
            //allCountriesFragment?.isVisible(true)
            allCountriesFragment!!
        }
    }

    fun notifyPageChanged(position: Int) {
        currentPosition = position
        if (position == POSITION_TRACKED_COUNTRIES) {
            allCountriesFragment?.isVisible(false)
            trackedCountriesFragment?.isVisible(true)
        } else if (position == POSITION_ALL_COUNTRIES) {
            allCountriesFragment?.isVisible(true)
            trackedCountriesFragment?.isVisible(false)
        }
    }

    fun notifySettingsChanged() {
        trackedCountriesFragment?.notifySettingsChanged()
        allCountriesFragment?.notifySettingsChanged()
    }

    fun notifyFilterChanged(filter: String) {
        if (currentPosition == POSITION_ALL_COUNTRIES) {
            allCountriesFragment?.applyFilter(filter)
            //trackedCountriesFragment?.applyFilter(" ")
        } else if (currentPosition == POSITION_TRACKED_COUNTRIES) {
            trackedCountriesFragment?.applyFilter(filter)
            //allCountriesFragment?.applyFilter(" ")
        }
    }

    companion object {
        const val POSITION_ALL_COUNTRIES = 1
        const val POSITION_TRACKED_COUNTRIES = 0
    }

    fun setNested(value: Boolean){
        allCountriesFragment?.setNested(value)
        trackedCountriesFragment?.setNested(value)
    }
}