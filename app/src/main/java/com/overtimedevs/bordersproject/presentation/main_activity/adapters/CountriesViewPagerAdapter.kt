package com.overtimedevs.bordersproject.presentation.main_activity.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.countries_list_fragment.CountriesFragment

class CountriesViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment{
        return if(position == 0){
            CountriesFragment(true)
        } else {
            CountriesFragment(false)
        }
    }
}