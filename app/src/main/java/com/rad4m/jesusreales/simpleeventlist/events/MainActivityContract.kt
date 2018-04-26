package com.rad4m.jesusreales.simpleeventlist.events

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.base.BasePresenter
import com.rad4m.jesusreales.simpleeventlist.base.BaseView
import com.rad4m.jesusreales.simpleeventlist.base.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

interface MainActivityContract {

    interface View : BaseView<Presenter> {
        fun start()
    }

    interface Presenter : BasePresenter {

        fun deleteEvent(context: Context, event: Event)

    }

    interface Model {

        fun deleteEvent(context: Context, event: Event)

    }

}