package com.rad4m.jesusreales.simpleeventlist.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.model.Event
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity

class EventAdapter(context: Context, var events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventsViewHolder>(), View.OnLongClickListener {

    //private var events: List<Event> = ArrayList()
    private lateinit var listener: View.OnLongClickListener
    private val context: Context = context

    //init {
        //this.events = events
    //}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.cardview_event, parent, false)
        itemView.setOnLongClickListener(this)

        return EventsViewHolder(context, itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event: Event = events[position]

        holder.bindEvent(event)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun getEventByPos(position: Int): Event {
        return events[position]
    }

    override fun onLongClick(v: View?): Boolean {
        return listener.onLongClick(v)
    }

    fun setOnLongClickListener(listener: View.OnLongClickListener) {
        this.listener = listener
    }

    class EventsViewHolder constructor(var context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivPicture: ImageView? = itemView.findViewById(R.id.ivPicture)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)

        fun bindEvent(event: Event) {
            tvName.text = event.name
            tvLocation.text = event.location
            ivPicture?.setImageDrawable(event.picture)
            tvTime.text = event.time

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val aTime = event.date
            val formatted = sdf.format(aTime.time)
            tvDate.text = formatted



            tvLocation.setOnClickListener {
                Log.i("CLICK LOC", "hago lcick en la localizacion")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://maps.google.com/maps?q=${event.location}")

                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        }


    }

}