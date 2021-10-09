package com.overtimedevs.bordersproject.presentation.utils

import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel

object ListsConcatenator {
    fun withoutDuplicates(list1 : List<CountryCardItemViewModel>, list2: List<CountryCardItemViewModel>) : List<CountryCardItemViewModel> {
        var mList1 : MutableList<CountryCardItemViewModel>
        var mList2 : MutableList<CountryCardItemViewModel>
        if(list1.size > list2.size){
            mList1 = MutableList(list1.size){list1[it]}
            mList2 = MutableList(list2.size){list2[it]}
        } else {
            mList1 = MutableList(list2.size){list2[it]}
            mList2 = MutableList(list1.size){list1[it]}
        }

        for(item in mList1){
            item.setIsTracked(contains(item, mList2))
        }
         return mList1.toList()
    }
}

fun contains(item: CountryCardItemViewModel, list: List<CountryCardItemViewModel>) : Boolean{
    for(listItem in list){
        if(listItem.countryId == item.countryId){
            return true
        }
    }
    return false

}