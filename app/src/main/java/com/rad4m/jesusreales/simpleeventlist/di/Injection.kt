package com.rad4m.jesusreales.simpleeventlist.di

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.AppDatabase
import com.rad4m.jesusreales.simpleeventlist.data.EventDao

/**
 * Enables injection of data sources.
 */
object Injection {

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSource = provideUserDataSource(context)
        return ViewModelFactory(dataSource)
    }

    private fun provideUserDataSource(context: Context): EventDao {
        val database = AppDatabase.getInstance(context)
        return database.eventDao
    }
}
