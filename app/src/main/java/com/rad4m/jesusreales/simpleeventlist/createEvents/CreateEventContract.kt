package com.rad4m.jesusreales.simpleeventlist.createEvents

import com.rad4m.jesusreales.simpleeventlist.BasePresenter
import com.rad4m.jesusreales.simpleeventlist.BaseView

interface CreateEventContract {

    interface View : BaseView<Presenter> {

        fun initView()


    }

    interface Presenter : BasePresenter {

        fun onClick(view: android.view.View)


    }


}