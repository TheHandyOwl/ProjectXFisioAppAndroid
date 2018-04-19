package com.projectx.fisioapp.app.fragment

//import android.support.v4.app.Fragment
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.utils.formatValue

@Suppress("DEPRECATION")
class CalendarFragment : Fragment() {

    private var calendarView: CalendarView? = null
    private var onSelectedDateListener: OnSelectedDateListener? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater?.inflate(R.layout.fragment_calendar, container, false)
        calendarView = root?.findViewById(R.id.fisio_calendar)
        calendarView?.firstDayOfWeek = 2

        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            //Note that months are indexed from 0. So, 0 means january, 1 means February, 2 means march etc.
            val updatedMonth = month + 1

            val dayFormatted = formatValue(dayOfMonth)
            val monthFormatted = formatValue(updatedMonth)

            val date = "$year-$monthFormatted-$dayFormatted"

            onSelectedDateListener?.onSelectedDate(date)
        }

        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        onSelectedDateListener = null
    }

    private fun commonAttach(listener: Any?) {
        if (listener is OnSelectedDateListener) {
            onSelectedDateListener = listener
        }
    }

    interface OnSelectedDateListener {
        fun onSelectedDate(date: String)
    }


}
