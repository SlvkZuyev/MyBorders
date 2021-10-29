package com.overtimedevs.bordersproject.presentation.main_activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.databinding.ActivityMainBinding
import com.overtimedevs.bordersproject.domain.model.UserSettings
import com.overtimedevs.bordersproject.presentation.country_info_activity.CountryInfoActivity
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesViewPagerAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.fragments.settings_fragment.SettingsDialogue
import com.overtimedevs.bordersproject.R
import android.view.inputmethod.InputMethodManager
import com.overtimedevs.bordersproject.notifications.MyAlarmManager

class MainActivity : AppCompatActivity() {
    var viewPager: ViewPager2? = null
    lateinit var binding: ActivityMainBinding
    var searchView: SearchView? = null


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
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbar)
        initTextSwitchers(binding)
        setupViewPager()
        setupTabLayout()

        binding.btnSettings.setOnClickListener { onSettingsClick() }

        if (viewModel.isFirstOpen()) {
            onFirstOpen()
        }

        initAlarmManager()
    }

    private fun setupViewPager() {
        val viewPagerAdapter = CountriesViewPagerAdapter(this)
        viewPager = binding.viewPager
        viewPager?.adapter = viewPagerAdapter
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    onPageChanged(position)
                    viewPagerAdapter.notifyPageChanged(position)
                    super.onPageSelected(position)
                }
            })
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Tracked"
            } else {
                tab.text = "All"
            }
        }.attach()
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
        settingsDialogue.onNewSettingsApplied =
            { oldSettings, newSettings -> onSettingsApplied(oldSettings, newSettings) }
        settingsDialogue.show(supportFragmentManager, settingsDialogue.tag)

        binding.toolbar.collapseActionView()
        quitSearchMode()
    }

    private fun onSettingsApplied(oldSettings: UserSettings, newSettings: UserSettings) {
        (viewPager?.adapter as CountriesViewPagerAdapter).notifySettingsChanged()
        viewModel.notifySettingsChanged()

        if (oldSettings.originCountry != newSettings.originCountry) {
            val message = "Origin country changed to: ${newSettings.originCountry}"
            showSnackBar(message)
        } else
        if (oldSettings.isVaccinated != newSettings.isVaccinated){
            val message: String

            if(newSettings.isVaccinated){
                message = "You are vaccinated now"
            } else {
                message = "You are not vaccinated now"
            }

            showSnackBar(message)
        }

    }


    private fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
    }

    private fun onSearchIconClick(searchView: MenuItem) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)

        Log.d("SlvkLog", "Menu created ")
        val searchItem: MenuItem? = menu.findItem(R.id.app_bar_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView
        searchItem.setOnMenuItemClickListener {
            onSearchIconClick(it)
            true
        }

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyBoard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                changeQuery(newText!!)
                return true
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                binding.appBarLayout.setExpanded(false)
                setToolbarExpandEnabled(false)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                setToolbarExpandEnabled(true)
                return true
            }

        })
        return true
    }

    private fun hideKeyBoard() {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    fun changeQuery(query: String) {
        (viewPager?.adapter as CountriesViewPagerAdapter).notifyFilterChanged(query)
    }

    private fun onPageChanged(position: Int) {
        quitSearchMode()
        viewModel.onPageChanged(position)
    }

    private fun quitSearchMode() {
        setToolbarExpandEnabled(true)
        hideKeyBoard()

        searchView?.isIconified = true
        binding.toolbar.collapseActionView()
    }

    private fun setToolbarExpandEnabled(value: Boolean) {
        val adapter = (viewPager?.adapter as CountriesViewPagerAdapter)
        adapter.setNested(value)
    }


    private fun onFirstOpen() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tab.getTabAt(1)?.select()
        }, 500)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.btnSettings.performClick()
        }, 1000)
    }


    private fun initAlarmManager() {
        MyAlarmManager.setup(this)
    }

}