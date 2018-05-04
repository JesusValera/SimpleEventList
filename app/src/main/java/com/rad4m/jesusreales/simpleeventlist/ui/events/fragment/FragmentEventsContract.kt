package com.rad4m.jesusreales.simpleeventlist.ui.events.fragment

import com.rad4m.jesusreales.simpleeventlist.ui.BaseView
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

interface FragmentEventsContract {

    interface View : BaseView<Presenter> {

        fun start()

        fun setEventsIntoRecyclerView(cellElements : ArrayList<CellElement>)
    }

    interface Presenter {

        fun filterEvents(view: android.view.View, events: List<Event>)

    }

}