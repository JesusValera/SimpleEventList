package com.rad4m.jesusreales.simpleeventlist

import android.app.Activity
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.rad4m.jesusreales.simpleeventlist.dialog.DatePickerFragment
import com.rad4m.jesusreales.simpleeventlist.dialog.TimePickerFragment

import kotlinx.android.synthetic.main.activity_create_event.*

class CreateEvent : AppCompatActivity(), DatePickerFragment.DateDialogListener, TimePickerFragment.DateDialogListener {

    private lateinit var etName: EditText
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var etLocation: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)
        setSupportActionBar(toolbar)

        etName = findViewById(R.id.etName)
        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        etLocation = findViewById(R.id.etLocation)

        fab.setOnClickListener {
            val name = etName.text.toString()

            if (name == "") {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent()
            intent.putExtra("name", name)
            intent.putExtra("date", etDate.text.toString())
            intent.putExtra("time", etTime.text.toString())
            intent.putExtra("location", etLocation.text.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        etDate.setOnClickListener({
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.show(fragmentManager, "date")
        })

        etTime.setOnClickListener({
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(fragmentManager, "time")
        })
    }

    override fun onSelectedDate(dialog: DialogFragment, year: Int, month: Int, dayOfMonth: Int) {
        val date = "$year-$month-$dayOfMonth"
        etDate.setText(date)
    }

    override fun onSelectedDate(dialog: DialogFragment, hourOfDay: Int, minute: Int) {
        val time = "$hourOfDay:$minute"
        etTime.setText(time)
    }

}
