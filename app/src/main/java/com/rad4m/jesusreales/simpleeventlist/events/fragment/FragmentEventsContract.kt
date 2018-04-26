package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.base.BaseView
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

interface FragmentEventsContract {

    interface View : BaseView<Presenter> {

        fun start()

        fun setEventsIntoRecyclerView(cellElements : ArrayList<CellElement>)
    }

    interface Presenter {

        fun filterEvents(view: android.view.View)

    }

    interface Model {

        fun getEvents(context: Context) : List<Event>

    }

}