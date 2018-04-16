package com.rad4m.jesusreales.simpleeventlist.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.MainActivity
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.adapter.EventAdapter
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.model.Event
import java.util.ArrayList

class PastEvents : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var events: ArrayList<Event>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_future_events, container, false)

        val activity = activity as MainActivity
        events = activity.events
        recyclerView = view.findViewById(R.id.rvEvents)
        recyclerView.setHasFixedSize(true)
        val adapter = EventAdapter(view.context, events)

        if (adapter.itemCount == 0) {
            recyclerView.setBackgroundResource(R.drawable.ic_inbox_black_24dp)
        }

        adapter.setOnLongClickListener(View.OnLongClickListener {
            val position = recyclerView.getChildAdapterPosition(it)
            val event = adapter.getEventByPos(position)
            val eventOption = EventOptions()
            eventOption.setEvent(event)
            eventOption.show(activity.fragmentManager, "tag")

            return@OnLongClickListener true
        })

        if (adapter.itemCount != 0) {
            view.findViewById<ImageView>(R.id.ivRecyclerViewEmpty).visibility = View.INVISIBLE
            view.findViewById<TextView>(R.id.tvRecyclerViewEmpty).visibility = View.INVISIBLE
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    companion object {
        fun newInstance(): PastEvents = PastEvents()
    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter.notifyDataSetChanged()
    }
}