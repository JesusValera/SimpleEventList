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

    override lateinit var mPresenter: MainActivityContract.Presenter

    val cellElements: ArrayList<CellElement> = ArrayList()
    private lateinit var mViewPager: ViewPager
    private lateinit var mAdapter: SampleAdapter
    private val INTENT_FOR_RESULT = 1
    private val INTENT_FOR_RESULT_UPDATE = 2

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initToolbar()

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        mViewPager = findViewById(R.id.view_pager)
        mAdapter = SampleAdapter(supportFragmentManager)

        mViewPager.adapter = mAdapter
        tabLayout.setupWithViewPager(mViewPager)

        CreateDemoEvents(applicationContext).createDemoEvents(cellElements)

        mPresenter = MainActivityPresenter(this)
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
                mPresenter.createEventActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            INTENT_FOR_RESULT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Insert event into SQLite.
                    val pic = CreateDemoEvents(baseContext).getRandomPicture()
                    mPresenter.createEventFromResult(data, pic)
                }
            }
            INTENT_FOR_RESULT_UPDATE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val pic = CreateDemoEvents(baseContext).getRandomPicture()
                    mPresenter.createEventFromResultUpdate(data, pic)
                }
            }
        }
        mPresenter.updateCurrentFragment(mAdapter.mCurrentFragment as BaseFragment)
        super.onActivityResult(requestCode, resultCode, data)
    }*/

    override fun addEventToEventList(event: Event) {
        cellElements.add(CellElement(event))
    }

    override fun updateEventFromEventList(event: Event) {
        // Search from array and replace. -- TODO Search from Room SQLite.
        // --> mPresenter.loadEvents()
        for (i in cellElements.indices) {
            if (cellElements[i].event!!.name == event.name) {
                // Set the same picture.
                event.picture = cellElements[i].event?.picture!!
                cellElements[i].event = event
            }
        }
    }

    override fun onDialogDelete(dialog: DialogFragment, cellElement: CellElement) {
        mPresenter.deleteEvent(cellElements, cellElement)
        mPresenter.updateCurrentFragment(mAdapter.mCurrentFragment as BaseFragment)
    }

    override fun onDialogAddToCalendar(dialog: DialogFragment, cellElement: CellElement) {
        mPresenter.addEventToCalendar(cellElement.event!!)
    }

    override fun onDialogUpdate(dialog: DialogFragment, cellElement: CellElement) {
        val intent = Intent(applicationContext, CreateEvent::class.java)
        mPresenter.updateEvent(cellElement.event!!, intent)
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

