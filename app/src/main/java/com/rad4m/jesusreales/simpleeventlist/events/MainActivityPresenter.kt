package com.rad4m.jesusreales.simpleeventlist.events

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.rad4m.jesusreales.simpleeventlist.data.CreateDemoEvents
import com.rad4m.jesusreales.simpleeventlist.events.fragment.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import java.text.SimpleDateFormat
import java.util.*

class MainActivityPresenter(val mainActivity: MainActivityContract.View): MainActivityContract.Presenter {

    override fun start() {

    }

    override fun addEventToCalendar(event: Event) {
        val beginDate = returnCompleteDate(event, event.startTime)
        val endDate = returnCompleteDate(event, event.endTime)

        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(CalendarContract.Events.TITLE, event.name)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginDate.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.timeInMillis)

        mainActivity.startEventCalendar(intent)
    }

    private fun returnCompleteDate(event: Event, time: String) : Calendar {
        val date = event.date
        val hours = time.subSequence(0, 2).toString().toInt()
        val minutes = time.subSequence(3, 5).toString().toInt()

        val cal: Calendar = GregorianCalendar()
        cal.time = date
        cal.add(Calendar.HOUR, hours)
        cal.add(Calendar.MINUTE, minutes)

        return cal
    }

    override fun updateEvent(event: Event, intent: Intent) {
        intent.putExtra("name", event.name)
        intent.putExtra("location", event.location)
        intent.putExtra("startTime", event.startTime)
        intent.putExtra("endTime", event.endTime)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateformat = sdf.format(event.date)
        intent.putExtra("date", dateformat)

        mainActivity.startUpdateEvent(intent)
    }

    override fun deleteEvent(cellElements: ArrayList<CellElement>, cellElement: CellElement) {
        cellElements.remove(cellElement)
    }

    override fun updateCurrentFragment(fragment: BaseFragment) {
        fragment.recreateAdapter(fragment.view!!)
    }

    override fun createEventFromResult(data: Intent, context: Context): Event {
        val name = data.getStringExtra("name")
        val event = Event(name)
        event.startTime = data.getStringExtra("startTime")
        event.endTime = data.getStringExtra("endTime")
        event.location = data.getStringExtra("location")
        event.picture = CreateDemoEvents(context).getRandomPicture()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        event.date = sdf.parse(data.getStringExtra("date"))

        return event
    }

    override fun createEvent(intent: Intent) {
        mainActivity.startCreateEvent(intent)
    }
}