package com.rad4m.jesusreales.simpleeventlist.events.fragment

import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement

class FragmentEventPresenter(
        private val cellElement: CellElement, // TODO Should get the data from Model.
        val baseFragment: FragmentEventsContract.View
) : FragmentEventsContract.Presenter {

    init {
        baseFragment.presenter = this
    }

    /*override fun addEventToCalendar(event: Event) {

    }

    override fun updateEvent(event: Event) {

    }

    override fun deleteEvent(event: Event) {

    }*/

}