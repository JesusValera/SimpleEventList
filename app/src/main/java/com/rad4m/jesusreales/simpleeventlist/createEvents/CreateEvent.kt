package com.rad4m.jesusreales.simpleeventlist.createEvents

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.rad4m.jesusreales.simpleeventlist.R
import com.rad4m.jesusreales.simpleeventlist.data.model.Event
import com.rad4m.jesusreales.simpleeventlist.dialog.DatePickerFragment
import com.rad4m.jesusreales.simpleeventlist.dialog.TimePickerFragment

import kotlinx.android.synthetic.main.activity_create_event.*
import java.text.SimpleDateFormat
import java.util.*

class CreateEvent : AppCompatActivity(), DatePickerFragment.DateDialogListener, TimePickerFragment.DateDialogListener, CreateEventContract.View {

    override lateinit var mPresenter: CreateEventContract.Presenter
    private lateinit var etName: EditText
    private lateinit var etDate: EditText
    private lateinit var etStartTime: EditText
    private lateinit var etEndTime: EditText
    private lateinit var etLocation: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        setSupportActionBar(toolbar)

        mPresenter = CreateEventPresenter(this)

        updatingEvent()

        listeners()

        fab.setOnClickListener {
            val name = etName.text.toString()
            val date = etDate.text.toString()
            val startTime = etStartTime.text.toString()
            val endTime = etEndTime.text.toString()
            val location = etLocation.text.toString()

            if (name == "" || date == "" || startTime == "" || endTime == "") {
                Toast.makeText(this, "Only location can be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val event = Event()
            event.name = name
            event.startTime = startTime
            event.endTime = endTime
            event.location = location
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            event.date = sdf.parse(date)

            mPresenter.updateOrInsertEvent(applicationContext, event)
        }
    }

    override fun finalizeActivity() {
        finish()
    }

    override fun initView() {
        etName = findViewById(R.id.etName)
        etDate = findViewById(R.id.etDate)
        etStartTime = findViewById(R.id.etStartTime)
        etEndTime = findViewById(R.id.etEndTime)
        etLocation = findViewById(R.id.etLocation)
    }

    private fun updatingEvent() {
        if (intent.hasExtra("name")) {
            etName.isFocusable = false
            etName.isFocusableInTouchMode = false
            etName.isClickable = false
            etName.keyListener = null

            etName.setText(intent.getStringExtra("name"))
            etDate.setText(intent.getStringExtra("date"))
            etStartTime.setText(intent.getStringExtra("startTime"))
            etEndTime.setText(intent.getStringExtra("endTime"))
            etLocation.setText(intent.getStringExtra("location"))
        }
    }

    private fun listeners() {
        etDate.setOnClickListener({
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(fragmentManager, "date")
        })

        etStartTime.setOnClickListener({
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(fragmentManager, "startTime")
        })

        etEndTime.setOnClickListener({
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(fragmentManager, "endTime")
        })
    }

    override fun onSelectedDate(dialog: DialogFragment, year: Int, month: Int, dayOfMonth: Int) {
        val date = "$year-${setTwoNumbersToTime(month)}-${setTwoNumbersToTime(dayOfMonth)}"
        etDate.setText(date)
    }

    override fun onSelectedDate(dialog: DialogFragment, hourOfDay: Int, minute: Int, tag: String) {
        val time = "${setTwoNumbersToTime(hourOfDay)}:${setTwoNumbersToTime(minute)}"
        when (tag) {
            "startTime" -> etStartTime.setText(time)
            "endTime" -> etEndTime.setText(time)
        }
    }

    private fun setTwoNumbersToTime(number: Int) = if (number.toString().length < 2) {
        "0" + number.toString()
    } else {
        number.toString()
    }

}
