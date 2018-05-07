package com.rad4m.jesusreales.simpleeventlist.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rad4m.jesusreales.simpleeventlist.data.EventDao

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val dataSource: EventDao) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
