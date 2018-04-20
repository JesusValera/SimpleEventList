package com.rad4m.jesusreales.simpleeventlist

import android.app.Activity
import android.app.DialogFragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.model.Event
import android.provider.CalendarContract.Events
import android.provider.CalendarContract
import android.support.design.widget.TabLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.rad4m.jesusreales.simpleeventlist.adapter.SampleAdapter
import com.rad4m.jesusreales.simpleeventlist.fragment.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.model.CellElement
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), EventOptions.DialogEventListener {

    val cellElements: ArrayList<CellElement> = ArrayList()
    private val INTENT_FOR_RESULT = 1
    private val INTENT_FOR_RESULT_UPDATE = 2
    private val pictures = arrayListOf(R.drawable.comp0, R.drawable.comp1, R.drawable.comp2, R.drawable.comp3)
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: SampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        adapter = SampleAdapter(supportFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        createDemoEvents()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun createDemoEvents() {
        val dates = createDates()
        for (i in 1..5) {
            val event = Event("Event n${i}")
            event.picture = ResourcesCompat.getDrawable(resources, pictures[random(4)], null)
            event.date = dates[i - 1]
            event.startTime = "10:00"
            event.endTime = "13:30"
            event.location = "Kazimierza Kordylewskiego 7-5, Kraków"
            val cellElement = CellElement(event)
            cellElements.add(cellElement)
        }
    }

    private fun createDates(): List<Date> {
        val date = Date()
        val date2 = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        val date3 = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        val date4 = Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 2)

        return listOf(date, date2, date2, date3, date4)
    }

    private fun random(n: Int) = (Math.random() * n).toInt()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuCreateEvent -> createEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createEvent() {
        val intent = Intent(this.applicationContext, CreateEvent::class.java)
        startActivityForResult(intent, INTENT_FOR_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            INTENT_FOR_RESULT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val event = createEventFromResult(data)
                    cellElements.add(CellElement(event))

                }
            }
            INTENT_FOR_RESULT_UPDATE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val event = createEventFromResult(data)
                    // Search from array and replace.
                    for (i in cellElements.indices) {
                        if (cellElements[i].event!!.name == event.name) {
                            // Set the same picture.
                            event.picture = cellElements[i].event?.picture
                            // Replace.
                            cellElements[i].event = event
                        }
                    }
                }
            }
        }
        updateCurrentFrag()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createEventFromResult(data: Intent): Event {
        val name = data.getStringExtra("name")
        val event = Event(name)
        event.startTime = data.getStringExtra("startTime")
        event.endTime = data.getStringExtra("endTime")
        event.location = data.getStringExtra("location")
        event.picture = ResourcesCompat.getDrawable(resources, pictures[random(4)], null)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        event.date = sdf.parse(data.getStringExtra("date"))

        return event
    }

    override fun onDialogDelete(dialog: DialogFragment, cellElement: CellElement) {
        cellElements.remove(cellElement)
        updateCurrentFrag()
    }

    private fun updateCurrentFrag() {
        val fragment = adapter.mCurrentFragment
        if (fragment is BaseFragment) {
            fragment.recreateAdapter(fragment.view!!)
        }
    }

    override fun onDialogAddToCalendar(dialog: DialogFragment, cellElement: CellElement) {

        val beginDate = returnCompleteDate(cellElement.event!!, cellElement.event!!.startTime)
        val endDate = returnCompleteDate(cellElement.event!!, cellElement.event!!.endTime)

        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = Events.CONTENT_URI
        intent.putExtra(Events.TITLE, cellElement.event?.name)
        intent.putExtra(Events.EVENT_LOCATION, cellElement.event?.location)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginDate.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate.timeInMillis)

        startActivity(intent)
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

    override fun onDialogUpdate(dialog: DialogFragment, cellElement: CellElement) {
        val event = cellElement.event
        val intent = Intent(this.applicationContext, CreateEvent::class.java)
        intent.putExtra("name", event!!.name)
        intent.putExtra("location", event.location)
        intent.putExtra("startTime", event.startTime)
        intent.putExtra("endTime", event.endTime)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateformat = sdf.format(event.date)
        intent.putExtra("date", dateformat)

        startActivityForResult(intent, INTENT_FOR_RESULT_UPDATE)
        //Log.i("dialogUpdate", "update ${cellElement.event?.name}")
    }

}

