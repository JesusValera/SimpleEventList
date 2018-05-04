package com.rad4m.jesusreales.simpleeventlist.ui.events

import com.rad4m.jesusreales.simpleeventlist.ui.BasePresenter
import com.rad4m.jesusreales.simpleeventlist.ui.BaseView

interface MainActivityContract {

    interface View : BaseView<Presenter> {

        fun startActivityCreateEvent()
    }

    interface Presenter : BasePresenter {

        fun activityCreateEvent()

    }

}