package com.rad4m.jesusreales.simpleeventlist.ui.events

import android.app.DialogFragment
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.*
import com.rad4m.jesusreales.simpleeventlist.ui.dialogs.EventOptions
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import android.support.v7.widget.Toolbar
import android.util.Log
import com.rad4m.jesusreales.simpleeventlist.di.EventViewModel
import com.rad4m.jesusreales.simpleeventlist.di.Injection
import com.rad4m.jesusreales.simpleeventlist.ui.createEvents.CreateEvent
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.ui.adapters.SampleAdapter
import com.rad4m.jesusreales.simpleeventlist.data.model.CellElement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EventOptions.DialogEventListener, MainActivityContract.View {

    override lateinit var mPresenter: MainActivityContract.Presenter
    private lateinit var mAdapter: SampleAdapter
    private lateinit var viewModel: EventViewModel
    private val disposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()
        val viewModelFactory = Injection.provideViewModelFactory(applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        mAdapter = SampleAdapter(supportFragmentManager)
        view_pager.adapter = mAdapter
        tab_layout.setupWithViewPager(view_pager)

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
        disposable.add(viewModel.deleteEvent(cellElement.event!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},
                        { error -> Log.e("ERROR SUBS", "Unable to delete event.", error) }))
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
