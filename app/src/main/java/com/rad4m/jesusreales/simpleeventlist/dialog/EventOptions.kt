package com.rad4m.jesusreales.simpleeventlist.dialog

import android.app.DialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.app.Activity
import com.rad4m.jesusreales.simpleeventlist.model.Event

class EventOptions : DialogFragment() {

    private lateinit var mListener: DialogEventListener
    private lateinit var event: Event

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val items = arrayOf("Add event to calendar", "Update event", "Delete event")

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("What do you want to do? (${event.name})")
                .setItems(items) { dialog, item ->
                    when (item) {
                        0 -> mListener.onDialogAddToCalendar(this, event)
                        1 -> mListener.onDialogUpdate(this, event)
                        2 -> mListener.onDialogDelete(this, event)
                    }
                }
        //builder.show()
        return builder.create()
    }

    fun setEvent(event: Event) {
        this.event = event
    }

    interface DialogEventListener {
        fun onDialogAddToCalendar(dialog: DialogFragment, event: Event)
        fun onDialogUpdate(dialog: DialogFragment, event: Event)
        fun onDialogDelete(dialog: DialogFragment, event: Event)
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