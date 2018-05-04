package com.rad4m.jesusreales.simpleeventlist.events

import android.app.DialogFragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.*
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import android.support.v7.widget.Toolbar
import com.rad4m.jesusreales.simpleeventlist.createEvents.CreateEvent
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.adapter.SampleAdapter
import com.rad4m.jesusreales.simpleeventlist.base.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EventOptions.DialogEventListener, MainActivityContract.View {

    override lateinit var mPresenter: MainActivityContract.Presenter
    private lateinit var mAdapter: SampleAdapter

    override fun start() {
        val fragment = mAdapter.mCurrentFragment as BaseFragment
        fragment.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        mAdapter = SampleAdapter(supportFragmentManager)
        view_pager.adapter = mAdapter
        tab_layout.setupWithViewPager(view_pager)

        mPresenter = MainActivityPresenter(this)
        //insertEvent()
    }

    private fun insertEvent() {
        val event = Event()
        event.name = "Guten Tag"
        event.location = "Krakow"
        event.date = Date(/*System.currentTimeMillis() - 1000 * 60 * 60 * 24*/)
        event.startTime = "12:10"
        event.endTime = "13:34"
        //AppDatabase.getDatabase(baseContext).eventDao.insert(event)
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuCreateEvent -> {
                mPresenter.activityCreateEvent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun startActivityCreateEvent() {
        val intent = Intent(this.applicationContext, CreateEvent::class.java)
        startActivity(intent)
    }

    override fun onDialogDelete(dialog: DialogFragment, cellElement: CellElement) {
        mPresenter.deleteEvent(baseContext, cellElement.event!!)
    }

    override fun onDialogAddToCalendar(dialog: DialogFragment, cellElement: CellElement) {
        val event = cellElement.event!!
        val beginDate = returnCompleteDate(event, event.startTime!!)
        val endDate = returnCompleteDate(event, event.endTime!!)

        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(CalendarContract.Events.TITLE, event.name)
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginDate.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.timeInMillis)

        startActivity(intent)
    }

    private fun returnCompleteDate(event: Event, time: String): Calendar {
        val date = event.date
        val hours = time.subSequence(0, 2).toString().toInt()
        val minutes = time.subSequence(3, 5).toString().toInt()

        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.HOUR, hours)
        cal.add(Calendar.MINUTE, minutes)

        return cal
    }

    override fun onDialogUpdate(dialog: DialogFragment, cellElement: CellElement) {
        val intent = Intent(this, CreateEvent::class.java)
        val event: Event = cellElement.event!!
        intent.putExtra("name", event.name)
        intent.putExtra("location", event.location)
        intent.putExtra("startTime", event.startTime)
        intent.putExtra("endTime", event.endTime)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateformat = sdf.format(event.date)
        intent.putExtra("date", dateformat)
        startActivity(intent)
    }

}
