package com.rad4m.jesusreales.simpleeventlist.di

import android.arch.lifecycle.ViewModel
import com.rad4m.jesusreales.simpleeventlist.data.EventDao
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable

class EventViewModel(private val dataSource: EventDao) : ViewModel() {

    fun getAll(): Flowable<List<Event>> {
        return dataSource.all()
    }

    fun insertOrUpdateEvent(event: Event): Completable {
        return Completable.fromAction {
            dataSource.insertOrUpdate(event)
        }
    }

    fun deleteEvent(event: Event): Completable {
        return Completable.fromAction {
            dataSource.delete(event)
        }
    }

}
