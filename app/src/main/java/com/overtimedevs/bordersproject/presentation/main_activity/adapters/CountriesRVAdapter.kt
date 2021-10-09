package com.overtimedevs.bordersproject.presentation.main_activity.adapters


import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.databinding.ItemCountryCardBinding
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel

interface OnClickListener{
    fun onCardClick(countryCardItemViewModel: CountryCardItemViewModel)
    fun onStarClick(countryCardItemViewModel: CountryCardItemViewModel)
}

class CountriesRVAdapter
    (
    private var localDataSet: MutableList<CountryCardItemViewModel>
) :
    RecyclerView.Adapter<CountriesRVAdapter.ViewHolder>() {

    private var onClickListener : OnClickListener? = null

    fun setOnClickLister(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    fun setNewList(newData: List<CountryCardItemViewModel>) {
        localDataSet = newData.toMutableList()
        notifyDataSetChanged()
    }

    fun removeCountry(countryCardItemViewModel: CountryCardItemViewModel) {
        var ind = 0
        var countrycd: CountryCardItemViewModel? = null
        for ((index, country) in localDataSet.withIndex()) {
            if (country.countryId == countryCardItemViewModel.countryId) {
                ind = index
                countrycd = country
                break
            }
        }

        localDataSet.remove(countrycd)
        notifyItemRemoved(ind)
        notifyItemRangeChanged(0, localDataSet.size)
    }

    class ViewHolder(
        var binding: ItemCountryCardBinding,
        val onClickListener: OnClickListener?,
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
        viewHolder.setCountryCard(localDataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return localDataSet.size
    }
}