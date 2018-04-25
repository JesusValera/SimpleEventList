package com.rad4m.jesusreales.simpleeventlist.events

import android.app.Activity
import android.app.DialogFragment
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.rad4m.jesusreales.simpleeventlist.dialog.EventOptions
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.rad4m.jesusreales.simpleeventlist.createEvents.CreateEvent
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.adapter.SampleAdapter
import com.rad4m.jesusreales.simpleeventlist.data.CreateDemoEvents
import com.rad4m.jesusreales.simpleeventlist.events.fragment.BaseFragment
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import java.util.*

class MainActivity : AppCompatActivity(), EventOptions.DialogEventListener, MainActivityContract.View {

    override lateinit var presenter: MainActivityContract.Presenter

    val cellElements: ArrayList<CellElement> = ArrayList()
    private val INTENT_FOR_RESULT = 1
    private val INTENT_FOR_RESULT_UPDATE = 2
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: SampleAdapter

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initToolbar()

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
        adapter = SampleAdapter(supportFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        CreateDemoEvents(applicationContext).createDemoEvents(cellElements)

        presenter = MainActivityPresenter(this)
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
                val intent = Intent(this.applicationContext, CreateEvent::class.java)
                presenter.createEvent(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            INTENT_FOR_RESULT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Insert event into SQLite.
                    val event: Event = presenter.createEventFromResult(data, applicationContext)
                    cellElements.add(CellElement(event))
                }
            }
            INTENT_FOR_RESULT_UPDATE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val event = presenter.createEventFromResult(data, applicationContext)
                    // Search from array and replace. -- TODO Search from Room SQLite.
                    // --> presenter.loadEvents()
                    for (i in cellElements.indices) {
                        if (cellElements[i].event!!.name == event.name) {
                            // Set the same picture.
                            event.picture = cellElements[i].event?.picture!!
                            cellElements[i].event = event
                        }
                    }
                }
            }
        }
        presenter.updateCurrentFragment(adapter.mCurrentFragment as BaseFragment)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDialogDelete(dialog: DialogFragment, cellElement: CellElement) {
        presenter.deleteEvent(cellElements, cellElement)
        presenter.updateCurrentFragment(adapter.mCurrentFragment as BaseFragment)
    }

    override fun onDialogAddToCalendar(dialog: DialogFragment, cellElement: CellElement) {
        presenter.addEventToCalendar(cellElement.event!!)
    }

    override fun onDialogUpdate(dialog: DialogFragment, cellElement: CellElement) {
        val intent = Intent(applicationContext, CreateEvent::class.java)
        presenter.updateEvent(cellElement.event!!, intent)
    }

    override fun startEventCalendar(intent: Intent) {
        startActivity(intent)
    }

    override fun startUpdateEvent(intent: Intent) {
        startActivityForResult(intent, INTENT_FOR_RESULT_UPDATE)
    }

    override fun startCreateEvent(intent: Intent) {
        startActivityForResult(intent, INTENT_FOR_RESULT)
    }
}

