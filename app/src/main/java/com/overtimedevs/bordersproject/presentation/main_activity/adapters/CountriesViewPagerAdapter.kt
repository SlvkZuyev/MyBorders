package com.overtimedevs.bordersproject.presentation.main_activity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment.AllCountriesFragment
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.tracked_countries_fragment.TrackedCountriesFragment

class CountriesViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment{
        return if(position == 0){
            TrackedCountriesFragment()
        } else {
            AllCountriesFragment()
        }
    }
}