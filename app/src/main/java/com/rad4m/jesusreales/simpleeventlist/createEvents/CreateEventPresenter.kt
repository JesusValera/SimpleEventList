package com.rad4m.jesusreales.simpleeventlist.createEvents

import android.content.Context
import com.rad4m.jesusreales.simpleeventlist.data.model.Event

class CreateEventPresenter(private val mView: CreateEventContract.View) : CreateEventContract.Presenter {

    private var mModel: CreateEventContract.Model = CreateEventModel()

    override fun start() {}

    init {
        mView.initView()
    }

    override fun updateOrInsertEvent(context: Context, event: Event) {
        mModel.updateOrInsertEvent(context, event)
        mView.finalizeActivity()
    }

}