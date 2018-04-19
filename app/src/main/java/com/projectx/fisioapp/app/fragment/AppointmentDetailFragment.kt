package com.projectx.fisioapp.app.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.utils.formatDate
import com.projectx.fisioapp.domain.model.Appointment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_appointment_detail.*

class AppointmentDetailFragment : Fragment() {

    companion object {
        private val EXTRA_APPOINTMENT = "EXTRA_APPOINTMENT"

        fun newInstance(appointment: Appointment): AppointmentDetailFragment{
            val fragment = AppointmentDetailFragment()
            val arguments = Bundle()
            arguments.putSerializable(EXTRA_APPOINTMENT, appointment)
            fragment.arguments = arguments

            return fragment
        }
    }

    private lateinit var root: View
    private lateinit var appointmentDetail: Appointment
    private var appointmentDetailListener: AppointmentDetailListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        inflater?.let {
            root = it.inflate(R.layout.fragment_appointment_detail, container, false)

        }

        return root
    }

    override fun onResume() {
        super.onResume()

        appointmentDetail = arguments.getSerializable(EXTRA_APPOINTMENT) as Appointment


        appointment_detail_customer_name_label.text = appointmentDetail.customerName
        appointment_detail_time_label.text = formatDate(appointmentDetail.date)
        appointment_detail_customer_price_label.text = appointmentDetail.servicePrice
        appointment_detail_address_label.text = appointmentDetail.address

        detail_appointment_cancelled.isChecked = appointmentDetail.isCancelled
        detail_appointment_confirmed.isChecked = appointmentDetail.isConfirmed

        // If the appointment is cancelled, the user cannot edit that info
        if(appointmentDetail.isCancelled){
            detail_appointment_confirmed.isClickable = false
            detail_appointment_cancelled.isClickable = false

            btnSave.hide()
        }


        if (appointmentDetail.extraInfo.isNotEmpty()){
            appointment_detail_extra_info_label.text = appointmentDetail.extraInfo
        } else {
            appointment_detail_extra_info_label.text = getString(R.string.no_extra_info)
        }

        //mapImage = root.findViewById(R.id.appointment_detail_map_image)
        val url = "http://maps.googleapis.com/maps/api/staticmap?center=${appointmentDetail.latitude},${appointmentDetail.longitude}&zoom=16&size=320x220&scale=2&markers=color:blue%7C${appointmentDetail.latitude},${appointmentDetail.longitude}"
        Picasso.with(activity).load(url).into(appointment_detail_map_image)

        appointment_detail_map_image.setOnClickListener({

            val uri = Uri.parse("google.navigation:q=${appointmentDetail.latitude},${appointmentDetail.longitude}&mode=w")
            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
            mapIntent.`package` = "com.google.android.apps.maps"
            startActivity(mapIntent)
        })

        btnSave.setOnClickListener({
            saveAppointment()
        })

    }

    private fun saveAppointment() {

        val appointmentUpdated = getAppointmentData()
        appointmentDetailListener?.onSavePressed(root.rootView, appointmentUpdated)

    }

    private fun getAppointmentData(): Appointment {
        return Appointment(appointmentDetail.id,
                appointment_detail_customer_price_label.text.toString(),
                appointment_detail_customer_name_label.text.toString(),
                appointment_detail_address_label.text.toString(),
                detail_appointment_confirmed.isChecked,
                detail_appointment_cancelled.isChecked,
                appointmentDetail.date,
                appointmentDetail.latitude,
                appointmentDetail.longitude,
                appointment_detail_extra_info_label.text.toString())
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonOnAttach(context)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonOnAttach(activity)
    }

    private fun commonOnAttach(context: Context?) {
        if (context is AppointmentDetailListener) {
            appointmentDetailListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        appointmentDetailListener = null
    }

}

interface AppointmentDetailListener {
    fun onSavePressed(view: View, item: Appointment)
}
