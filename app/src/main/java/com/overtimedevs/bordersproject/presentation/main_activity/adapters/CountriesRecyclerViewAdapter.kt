package com.overtimedevs.bordersproject.presentation.main_activity.adapters


import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ItemCountryCardBinding
import com.overtimedevs.bordersproject.domain.model.Country


class CountriesRecyclerViewAdapter
    (private val localDataSet: List<Country>) :
    RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemCountryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setCountry(country: Country){
            binding.country = country
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding: ItemCountryCardBinding = DataBindingUtil.inflate(inflater, R.layout.item_country_card, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setCountry(localDataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}