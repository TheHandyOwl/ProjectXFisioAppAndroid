package com.projectx.fisioapp.app.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.activity.CalendarActivity
import com.projectx.fisioapp.app.adapter.AppointmentItemRecyclerViewAdapter
import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.domain.model.Appointments

class AppointmentsListFragment : Fragment() {

    private var appointments: List<Appointment>? = null
    private lateinit var root: View
    private lateinit var mParent: CalendarActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_appointments_list, container, false)
        }

        return root
    }

    fun setupRecyclerView(recyclerView: RecyclerView, list: Appointments) {

        recyclerView.adapter = AppointmentItemRecyclerViewAdapter(mParent, list)
        recyclerView.layoutManager = GridLayoutManager(mParent.baseContext, 1)
    }

    fun setParent(calendarActivity: CalendarActivity) {

        mParent = calendarActivity
    }

    fun setAppointmentsList(items: List<Appointment>) {
        this.appointments = items
    }

    interface OnSelectedAppointmentListener {
        fun onSelectedAppointment(appointment: Appointment)
    }


}
