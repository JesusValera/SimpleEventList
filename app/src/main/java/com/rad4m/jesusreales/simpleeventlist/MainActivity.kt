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
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), EventOptions.DialogEventListener {

    lateinit var events: ArrayList<Event>
    val INTENT_FOR_RESULT = 1
    private val pictures = arrayListOf(R.drawable.comp0, R.drawable.comp1, R.drawable.comp2, R.drawable.comp3)

    fun test() {
        /*val numbersArray = Array(5, { j -> j * 2 })
        var arr = "";
        for (i in numbersArray) {
            arr += "$i, "
        }
        Log.i("Arry ->   ", arr)

        val name: String? = null
        Log.i("Null ->   ", "$name?.length")

        val number: Int? = null
        val num2 = number ?: 5
        Log.i("Null Elvis ->   ", num2.toString())

        val numbersArray2 = intArrayOf(1,2,3,4,5)
        for ((index, value) in numbersArray2.withIndex()) {
            print("$index index value is $value\n")
        }

        for (index in numbersArray2.indices) {
            print("$index indice...\n")
        }

        val event = Event("Event")
        if (event is Event) {
            print("Int is type of ANY\n")
        }

        print("Return ->>   ${1 > 2}\n")
        val range: IntProgression = 7 downTo 1 step 2

        for (n in range) {
            print("$n\n")
        }

        if (5 in 1..10) {
            print("Yes 5 is in the range\n") // prints "Yes 5 is in the range"
        }

        if (15 !in 1..10) {
            print("Yes 15 is in the range\n") // prints "Yes 5 is in the range"
        }

        val nonNullsList: List<Int> = listOfNotNull(2, 45, 2, null, 5, null)

        var names = arrayListOf("Chike", "Nnamdi", "Mgbemena")
        print("Position of Nnamadi: " + names.indexOf("Chike") + "\n")*/

        /*val intsSortedSet: TreeSet<Int>  = sortedSetOf(4, 1, 7, 2)
        intsSortedSet.add(6)
        intsSortedSet.remove(1)
        for (i in intsSortedSet) {
            println(i)
        }

        val currenciesMutableMap: MutableMap<String, String> = mutableMapOf("Naira" to "Nigeria", "Dollars" to "USA", "Pounds" to "UK")
        println("Countries are ${currenciesMutableMap.values}") // Countries are [Nigeria, USA, UK]
        println("Currencies are ${currenciesMutableMap.keys}") // Currencies are [Naira, Dollars, Pounds]
        currenciesMutableMap.put("Cedi", "Ghana")
        currenciesMutableMap.remove("Dollars")*/

        val range: IntProgression = 1..5

        for (n in range) {
            print("$n\n")
        }

        val range2: IntProgression = 1 until 5
        for (n2 in range2) {
            print("$n2\n")
        }

        val number: Int = 4
        //require(number > 5, { "Error, numero $number is less than 5" })
        val list: List<String> = listOf("in", "the", "club")
        Log.w("RESULTTTT:: ", list.last { it.length == 3 })


        val stringList = listOf("in", "the", "club")
        Log.w("RESULTTTT21:: ", stringList.last { it.length == 3 }) // will print "the"

        val strLenThree = stringList.last(fun(string): Boolean {
            return string.length == 3
        })
        Log.w("RESULTTTT22:: ", strLenThree)

        printCircumferenceAndArea(5.0)

        "test".upperCaseFirstLetter()

        Log.w("AREA 3.0", "" + circleOperation(3.0, ::calArea))
        circleOperation(3.0, {
            it * it
        })

    }

    fun String.upperCaseFirstLetter(): String {
        return this.substring(0, 1).toUpperCase().plus(this.substring(1))
    }

    fun printCircumferenceAndArea(radius: Double) {

        fun calCircumference(): Double = (2 * Math.PI) * radius
        val circumference = "%.2f".format(calCircumference())

        fun calArea(): Double = (Math.PI) * Math.pow(radius, 2.0)
        val area = "%.2f".format(calArea())

        Log.w("CIRCUMFERENCE", "The circumference is $circumference and the area is $area")
    }

    fun calCircumference(radius: Double) = (2 * Math.PI) * radius

    fun calArea(radius: Double): Double = (Math.PI) * Math.pow(radius, 2.0)

    fun circleOperation(radius: Double, op: (Double) -> Double): Double {
        val result = op(radius)
        return result
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        val adapter = SampleAdapter(supportFragmentManager)

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        /*tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })*/

        ////
        test()
        ////

        events = ArrayList()

        for (i in 1..3) {
            val event = Event("Event n${i}")
            event.picture = resources.getDrawable(pictures[random(4)])
            events.add(event)
        }
    }

    fun random(n: Int) = (Math.random() * n).toInt()

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
                        event.date = cal
                    } catch (e: Exception) {
                    }

                    event.addEvent(events)
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

