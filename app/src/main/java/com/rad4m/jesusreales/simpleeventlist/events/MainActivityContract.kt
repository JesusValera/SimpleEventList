package com.rad4m.jesusreales.simpleeventlist.events

import android.content.Context
import android.content.Intent
import com.rad4m.jesusreales.simpleeventlist.BasePresenter
import com.rad4m.jesusreales.simpleeventlist.BaseView
import com.rad4m.jesusreales.simpleeventlist.events.fragment.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

interface MainActivityContract {

    interface View : BaseView<Presenter> {

        fun startEventCalendar(intent: Intent)

        fun startUpdateEvent(intent: Intent)

        fun startCreateEvent(intent: Intent)

        fun addEventToEventList(event: Event)

        fun updateEventFromEventList(event: Event)
    }

    interface Presenter : BasePresenter {

        fun addEventToCalendar(event: Event)

        fun updateEvent(event: Event, intent: Intent)

        fun deleteEvent(cellElements : ArrayList<CellElement>, cellElement: CellElement)

        fun updateCurrentFragment(fragment: BaseFragment)

        fun createEventFromResult(data: Intent, picture: Int)

        fun createEventFromResultUpdate(data: Intent, picture: Int)

        fun createEventActivity(intent: Intent)
    }

}