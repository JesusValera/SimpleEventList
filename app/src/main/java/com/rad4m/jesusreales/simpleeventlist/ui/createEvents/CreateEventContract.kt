package com.rad4m.jesusreales.simpleeventlist.ui.createEvents

import com.rad4m.jesusreales.simpleeventlist.ui.BasePresenter
import com.rad4m.jesusreales.simpleeventlist.ui.BaseView

interface CreateEventContract {

    interface View : BaseView<Presenter> {

        fun initViews()

    }

    interface Presenter : BasePresenter

}