package com.overtimedevs.bordersproject.presentation.main_activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextSwitcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ActivityMainBinding
import com.overtimedevs.bordersproject.presentation.country_info_activity.CountryInfoActivity
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesViewPagerAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.settings_fragment.SettingsDialogue
import com.overtimedevs.bordersproject.presentation.test_settings_activity.TestSettingsActivity
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.widget.SearchView

import androidx.core.view.MenuItemCompat
import androidx.core.view.MenuItemCompat.getActionView


class MainActivity : AppCompatActivity() {
    var viewPager: ViewPager2? = null

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        val viewPagerAdapter = CountriesViewPagerAdapter(this)
        initTextSwitchers(binding)
        binding.lifecycleOwner = this
        viewPager = binding.viewPager
        viewPager?.adapter = viewPagerAdapter
        binding.viewModel = viewModel


        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewPagerAdapter.notifyPageChanged(position)
                    viewModel.onPageChanged(position)
                    super.onPageSelected(position)
                }
            })

        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Tracked"
            } else {
                tab.text = "All"
            }
        }.attach()

        binding.btnSettings.setOnClickListener { onSettingsClick() }
    }

    private fun initTextSwitchers(binding: ActivityMainBinding) {
        val textSwitchers = listOf(binding.openTv, binding.closedTv, binding.restrictionTv)

        for (switcher in textSwitchers) {
            switcher.setInAnimation(this, android.R.anim.fade_in)
            switcher.setOutAnimation(this, android.R.anim.fade_out)
            switcher.inAnimation.duration = 200
            switcher.outAnimation.duration = 200
        }
    }

    fun showCountryInfo(countryId: Int) {
        val intent = Intent(this, CountryInfoActivity::class.java)
        intent.putExtra("country_id", countryId)
        startActivity(intent)
    }

    private fun onSettingsClick() {
        val settingsDialogue = SettingsDialogue()

        settingsDialogue.onNewSettingsApplied = { onSettingsApplied() }

        settingsDialogue.show(supportFragmentManager, settingsDialogue.tag)
    }

    private fun onSettingsApplied() {
        (viewPager?.adapter as CountriesViewPagerAdapter).notifySettingsChanged()
        viewModel.notifySettingsChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        Log.d("SlvkLog", "Menu created ")
        val searchItem: MenuItem? = menu.findItem(R.id.app_bar_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SlvkLog", "New text $newText")
                (viewPager?.adapter as CountriesViewPagerAdapter).notifyFilterChanged(newText!!)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}