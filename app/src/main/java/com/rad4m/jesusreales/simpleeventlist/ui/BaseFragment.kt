package com.rad4m.jesusreales.simpleeventlist.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.di.EventViewModel
import com.rad4m.jesusreales.simpleeventlist.di.Injection
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.ui.adapters.EventAdapter
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.ui.dialogs.EventOptions
import com.rad4m.jesusreales.simpleeventlist.ui.events.fragment.FragmentEventsContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseFragment : Fragment(), FragmentEventsContract.View {

    override lateinit var mPresenter: FragmentEventsContract.Presenter
    protected lateinit var mView: View
    private lateinit var mAdapter: EventAdapter
    protected lateinit var mRecyclerView: RecyclerView

    private lateinit var viewModel: EventViewModel
    private val disposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()
        val viewModelFactory = Injection.provideViewModelFactory(activity!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)

        disposable.add(viewModel.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({mPresenter.filterEvents(view!!, it)},
                        { error -> Log.e("ERROR SUBS", "Unable to get events.", error) }))
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }

    fun recreateAdapter(view: View, cellElement: ArrayList<CellElement>) {
        mAdapter = EventAdapter(view.context, cellElement)
        mAdapter.setOnLongClickListener(View.OnLongClickListener {
            val position = mRecyclerView.getChildAdapterPosition(it)
            val element = mAdapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setCellElement(element)
            eventOption.show(activity?.fragmentManager, "tag")

            return@OnLongClickListener true
        })

        if (mAdapter.itemCount != 0) {
            view.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.INVISIBLE
            view.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.INVISIBLE
        } else {
            view.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.VISIBLE
        }

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setEventsIntoRecyclerView(cellElements: ArrayList<CellElement>) {
        recreateAdapter(mView, cellElements)
    }

}