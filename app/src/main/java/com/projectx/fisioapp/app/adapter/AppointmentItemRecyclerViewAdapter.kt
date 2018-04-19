package com.projectx.fisioapp.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.activity.CalendarActivity
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.utils.formatDate
import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.domain.model.Appointments
import kotlinx.android.synthetic.main.appointment_list_content.view.*

class AppointmentItemRecyclerViewAdapter(private val mParentActivity: CalendarActivity,
                                         private val mValues: Appointments?) :
        RecyclerView.Adapter<AppointmentItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Appointment
            Router.navigateFromCalendarActivityToAppointmentDetailActivity(mParentActivity, item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues!![position]
        holder.mIdView.text = formatDate(item.date)
        holder.mContentView.text = item.address

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.appointment_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues?.count() ?: 0

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.appointment_list_id
        val mContentView: TextView = mView.appointment_list_content_field
    }

}