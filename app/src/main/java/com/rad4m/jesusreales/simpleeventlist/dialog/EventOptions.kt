package com.rad4m.jesusreales.simpleeventlist.dialog

import android.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.app.Activity
import com.rad4m.jesusreales.simpleeventlist.model.CellElement
import com.rad4m.jesusreales.simpleeventlist.model.Event

class EventOptions : DialogFragment() {

    private lateinit var mListener: DialogEventListener
    private lateinit var cellElement: CellElement

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = arrayOf("Add event to calendar", "Update event", "Delete event")

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("What do you want to do? (${cellElement.event?.name})")
        builder.setItems(items) { _, item ->
            when (item) {
                0 -> mListener.onDialogAddToCalendar(this, cellElement)
                1 -> mListener.onDialogUpdate(this, cellElement)
                2 -> mListener.onDialogDelete(this, cellElement)
            }
        }
        return builder.create()
    }

    fun setCellElement(cellElement: CellElement) {
        this.cellElement = cellElement
    }

    interface DialogEventListener {
        fun onDialogAddToCalendar(dialog: DialogFragment, cellElement: CellElement)
        fun onDialogUpdate(dialog: DialogFragment, cellElement: CellElement)
        fun onDialogDelete(dialog: DialogFragment, cellElement: CellElement)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mListener = activity as DialogEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement DialogEventListener")
        }

    }
}