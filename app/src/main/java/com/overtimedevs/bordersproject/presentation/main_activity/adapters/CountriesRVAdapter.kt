package com.overtimedevs.bordersproject.presentation.main_activity.adapters


import android.annotation.SuppressLint
import android.content.ClipData
import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Filter

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ItemCountryCardBinding
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel
import java.util.*
import java.util.Collections.addAll
import kotlin.collections.ArrayList

interface OnClickListener{
    fun onCardClick(countryCardItemViewModel: CountryCardItemViewModel)
    fun onStarClick(countryCardItemViewModel: CountryCardItemViewModel)
}

class CountriesRVAdapter() :
    RecyclerView.Adapter<CountriesRVAdapter.ViewHolder>() {

    private val differCallback = object: DiffUtil.ItemCallback<CountryCardItemViewModel>() {
        override fun areItemsTheSame(oldItem: CountryCardItemViewModel, newItem: CountryCardItemViewModel): Boolean {
            return  oldItem.countryId == newItem.countryId &&
                    oldItem.message == newItem.message &&
                    oldItem.borderStatus == newItem.borderStatus &&
                    oldItem.trackStatus == newItem.trackStatus
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CountryCardItemViewModel, newItem: CountryCardItemViewModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    private var onClickListener : OnClickListener? = null
    var initialDataSet = ArrayList<CountryCardItemViewModel>()

    fun setOnClickLister(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    fun setNewList(newData: List<CountryCardItemViewModel>) {
        differ.submitList(newData)
        initialDataSet = ArrayList<CountryCardItemViewModel>().apply {
           addAll(newData)
        }
    }

    fun removeCountry(countryCardItemViewModel: CountryCardItemViewModel) {
        var countryId: CountryCardItemViewModel? = null
        val prevList = mutableListOf<CountryCardItemViewModel>()
        prevList.addAll(differ.currentList)

        for ((index, country) in prevList.withIndex()) {
            if (country.countryId == countryCardItemViewModel.countryId) {
                countryId = country
                break
            }
        }

        prevList.remove(countryId)
        differ.submitList(prevList)
    }

    class ViewHolder(
        var binding: ItemCountryCardBinding,
        private val onClickListener: OnClickListener?,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setCountryCard(countryItemViewModel: CountryCardItemViewModel) {
            binding.countryCard = countryItemViewModel

            binding.trackStatusIcon.setOnClickListener {
                onClickListener?.onStarClick(countryItemViewModel)
                countryItemViewModel.onStarClick()
                countryItemViewModel.onTrackStatusChanged?.invoke(countryItemViewModel)
            }

            binding.cardContainer.setOnClickListener {
                onClickListener?.onCardClick(countryItemViewModel)
                countryItemViewModel.onCountryClicked?.invoke(countryItemViewModel)
            }
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding: ItemCountryCardBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_country_card, viewGroup, false)

        return ViewHolder(binding, onClickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.setCountryCard(differ.currentList[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getFilter(): Filter {
        return cityFilter
    }

    private val cityFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<CountryCardItemViewModel> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                initialDataSet.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().lowercase()
                initialDataSet.forEach {
                    if (it.countryName.lowercase().contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                val filteredDataset = results.values as ArrayList<CountryCardItemViewModel>
                differ.submitList(filteredDataset)
            }
        }
    }

}