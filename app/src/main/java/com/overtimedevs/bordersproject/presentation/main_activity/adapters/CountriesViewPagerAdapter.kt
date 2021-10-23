package com.overtimedevs.bordersproject.presentation.main_activity.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment.AllCountriesFragment
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.tracked_countries_fragment.TrackedCountriesFragment

class CountriesViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var trackedCountriesFragment : TrackedCountriesFragment? = null
    private var allCountriesFragment  : AllCountriesFragment? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment{
        return if(position == 0){
            trackedCountriesFragment = TrackedCountriesFragment()
            //trackedCountriesFragment?.isVisible(true)
            trackedCountriesFragment!!
        } else {
            allCountriesFragment = AllCountriesFragment()
            //allCountriesFragment?.isVisible(true)
            allCountriesFragment!!
        }
    }

    fun notifyPageChanged(position: Int){
        if(position == 0){
            allCountriesFragment?.isVisible(false)
            trackedCountriesFragment?.isVisible(true)
        } else if(position == 1){
            allCountriesFragment?.isVisible(true)
            trackedCountriesFragment?.isVisible(false)
        }
    }

    fun notifySettingsChanged(){
        trackedCountriesFragment?.notifySettingsChanged()
        allCountriesFragment?.notifySettingsChanged()
    }
}