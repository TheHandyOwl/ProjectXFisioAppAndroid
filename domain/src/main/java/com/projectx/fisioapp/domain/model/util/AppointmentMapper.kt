package com.projectx.fisioapp.domain.model.util

import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.domain.model.Appointments
import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData

class AppointmentMapper {

    internal fun appointmentsListMapper(list: List<AppoinmentData>): Appointments {

        val tempList = ArrayList<Appointment>()

        list.forEach {
            tempList.add(appointmentMapper(it))
        }

        return Appointments(tempList)
    }


    private fun appointmentMapper(appointment: AppoinmentData): Appointment = Appointment(
            appointment.databaseId,
            appointment.servicePrice,
            appointment.customerName,
            appointment.address,
            appointment.isConfirmed,
            appointment.isCancelled,
            appointment.date,
            appointment.latitude,
            appointment.longitude,
            appointment.extraInfo
    )
}
