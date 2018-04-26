package com.rad4m.jesusreales.simpleeventlist.createEvents

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.base.BasePresenter
import com.rad4m.jesusreales.simpleeventlist.base.BaseView
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

interface CreateEventContract {

    interface View : BaseView<Presenter> {

        fun initView()

        fun finalizeActivity()

    }

    interface Presenter : BasePresenter {

        fun updateOrInsertEvent(context: Context, event: Event)

    }

    interface Model {

        fun updateOrInsertEvent(context: Context, event: Event)

    }

}