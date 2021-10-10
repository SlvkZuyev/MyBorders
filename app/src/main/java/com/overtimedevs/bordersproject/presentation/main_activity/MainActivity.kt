package com.overtimedevs.bordersproject.presentation.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ActivityMainBinding
import com.overtimedevs.bordersproject.presentation.country_info_activity.CountryInfoActivity
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesViewPagerAdapter

class MainActivity : AppCompatActivity() {
    //Todo: test if data is loading correctly if app is opened first time
    //Todo: Increase statistic`s fading out speed in coordinator layout
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


    //Todo: replace TextViews in statistic bar with TextSwitcher to perform smoother text switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewPagerAdapter = CountriesViewPagerAdapter(this)
        binding.lifecycleOwner = this

        binding.viewPager.adapter = viewPagerAdapter
        binding.viewModel = viewModel


        binding.viewPager.registerOnPageChangeCallback(
            object :ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    viewPagerAdapter.notifyPageChanged(position)
                    viewModel.onPageChanged(position)
                    super.onPageSelected(position)
                }
            })

        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            if(position == 0){
                tab.text = "Tracked"
            } else {
                tab.text = "All"
            }
        }.attach()

    }

    fun showCountryInfo(countryId: Int){
        val intent = Intent(this, CountryInfoActivity::class.java)
        intent.putExtra("country_id", countryId)
        startActivity(intent)
    }
}