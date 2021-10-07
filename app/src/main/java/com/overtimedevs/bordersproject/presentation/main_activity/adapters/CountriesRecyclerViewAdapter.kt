package com.overtimedevs.bordersproject.presentation.main_activity.adapters


import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ItemCountryCardBinding
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCard


class CountriesRecyclerViewAdapter
    (private var localDataSet: List<CountryCard>) :
    RecyclerView.Adapter<CountriesRecyclerViewAdapter.ViewHolder>() {
    var onTrackStatusChanged: ((CountryCard) -> Unit)? = null
    var onCountryClicked: ((CountryCard) -> Unit)? = null


    fun setNewList(newData: List<CountryCard>) {
        localDataSet = newData
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: ItemCountryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setCountryCard(country: CountryCard) {
            binding.countryCard = country
            binding.trackStatusIcon.setOnClickListener {
                country.isTracked = !country.isTracked
                if (country.isTracked) {
                    binding.trackStatusIcon.setImageResource(R.drawable.starr_checked)
                } else {
                    binding.trackStatusIcon.setImageResource(R.drawable.starr_unchecked)
                }

                country.onTrackStatusChanged?.invoke(country)
            }
            binding.cardContainer.setOnClickListener { country.onCountryClicked?.invoke(country) }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding: ItemCountryCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_country_card, viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setCountryCard(localDataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}