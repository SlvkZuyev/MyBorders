package com.overtimedevs.bordersproject.presentation.utils

import android.util.Log
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCard

object ListsConcatenator {
    fun withoutDuplicates(list1 : List<CountryCard>, list2: List<CountryCard>) : List<CountryCard> {
        var mList1 : MutableList<CountryCard>
        var mList2 : MutableList<CountryCard>
        if(list1.size > list2.size){
            mList1 = MutableList(list1.size){list1[it]}
            mList2 = MutableList(list2.size){list2[it]}
        } else {
            mList1 = MutableList(list2.size){list2[it]}
            mList2 = MutableList(list1.size){list1[it]}
        }

        for(item in mList1){
            item.isTracked = contains(item, mList2)
        }
         return mList1.toList()
    }
}

fun contains(item: CountryCard, list: List<CountryCard>) : Boolean{
    for(listItem in list){
        if(listItem.countryId == item.countryId){
            return true
        }
    }
    return false

}