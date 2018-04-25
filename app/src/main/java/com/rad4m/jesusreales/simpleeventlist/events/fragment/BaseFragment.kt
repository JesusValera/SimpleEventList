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
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import kotlin.collections.ArrayList

abstract class BaseFragment : Fragment(), FragmentEventsContract.View {

    override lateinit var presenter: FragmentEventsContract.Presenter

    protected lateinit var adapter: EventAdapter
    protected lateinit var recyclerView: RecyclerView

    abstract fun filterEvents() : ArrayList<CellElement>

    fun recreateAdapter(view: View) {
        adapter = EventAdapter(view.context, filterEvents())
        adapter.setOnLongClickListener(View.OnLongClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val element = adapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setCellElement(element)
            eventOption.show(activity?.fragmentManager, "tag")

            return@OnLongClickListener true
        })

        if (adapter.itemCount != 0) {
            view.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.INVISIBLE
            view.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.INVISIBLE
        } else {
            view.findViewById<ImageView>(R.id.ivRecyclerViewEmpty)?.visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.tvRecyclerViewEmpty)?.visibility = View.VISIBLE
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}