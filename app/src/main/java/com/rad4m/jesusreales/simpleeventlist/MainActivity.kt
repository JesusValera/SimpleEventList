package com.rad4m.jesusreales.simpleeventlist

import android.app.Activity
import android.app.DialogFragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.model.Event
import android.provider.CalendarContract.Events
import android.provider.CalendarContract
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.rad4m.jesusreales.simpleeventlist.adapter.SampleAdapter
import com.rad4m.jesusreales.simpleeventlist.model.CellElement
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), EventOptions.DialogEventListener {

    lateinit var cellElements: ArrayList<CellElement>
    val INTENT_FOR_RESULT = 1
    private val pictures = arrayListOf(R.drawable.comp0, R.drawable.comp1, R.drawable.comp2, R.drawable.comp3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val adapter = SampleAdapter(supportFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        cellElements = ArrayList()

        val dates = createDates()
        for (i in 1..5) {
            val event = Event("Event n${i}")
            event.picture = resources.getDrawable(pictures[random(4)])
            event.date = dates[i - 1]
            val cellElement = CellElement(event)
            cellElements.add(cellElement)
        }
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
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
        if (requestCode == INTENT_FOR_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val name = data.getStringExtra("name")
                    val event = Event(name)
                    event.location = data.getStringExtra("location")
                    event.picture = ContextCompat.getDrawable(applicationContext, pictures[random(4)])

                    try {
                        val aTime = data.getStringExtra("date")
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val cal = Calendar.getInstance()
                        cal.time = sdf.parse(aTime)
                        Log.i("DATE --> ", "" + cal.timeInMillis)
                        Log.i("DATE2 --> ", "" + cal)
                        //event.date = cal
                    } catch (e: Exception) {
                    }

                    cellElements.add(CellElement(event))
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDialogAddToCalendar(dialog: DialogFragment, event: Event) {
        val beginTime = Calendar.getInstance()
        beginTime.set(2018, 4, 12, 13, 30)

        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = Events.CONTENT_URI
        intent.putExtra(Events.TITLE, event.name)
        intent.putExtra(Events.EVENT_LOCATION, event.location)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())

        startActivity(intent)
    }

    override fun onDialogUpdate(dialog: DialogFragment, event: Event) {
        Log.i("dialogUpdate", "update ${event.name}")
        // Update event selected.
    }

    override fun onDialogDelete(dialog: DialogFragment, event: Event) {
        Log.i("dialogdelete", "detele ${event.name}")
        // Delete event selected.
    }

}

