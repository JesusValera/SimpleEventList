package com.rad4m.jesusreales.simpleeventlist.events.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.adapter.EventAdapter
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.events.MainActivity

abstract class BaseFragment : Fragment(), FragmentEventsContract.View {

    override lateinit var mPresenter: FragmentEventsContract.Presenter

    protected lateinit var mAdapter: EventAdapter
    protected lateinit var mRecyclerView: RecyclerView

    fun recreateAdapter(view: View) {
        val activity: MainActivity = activity as MainActivity
        mAdapter = EventAdapter(view.context, mPresenter.filterEvents(activity))
        mAdapter.setOnLongClickListener(View.OnLongClickListener {
            val position = mRecyclerView.getChildAdapterPosition(it)
            val element = mAdapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setCellElement(element)
            eventOption.show(activity.fragmentManager, "tag")

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

}