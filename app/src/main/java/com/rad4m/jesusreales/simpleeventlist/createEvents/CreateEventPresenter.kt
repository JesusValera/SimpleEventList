package com.rad4m.jesusreales.simpleeventlist.createEvents

import android.view.View
import com.rad4m.jesusreales.simpleeventlist.R

class CreateEventPresenter(private val mView: CreateEventContract.View) : CreateEventContract.Presenter, android.view.View.OnClickListener {

    /// MODEL
    //private var mModel: CreateEventContract.Model = CreateEventModel()

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> {
                //
            }
        }
    }

    override fun start() {

    }

    init {
        mView.initView()
    }


}