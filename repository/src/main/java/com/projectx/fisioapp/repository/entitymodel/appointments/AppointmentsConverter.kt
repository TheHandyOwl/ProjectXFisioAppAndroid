package com.projectx.fisioapp.repository.entitymodel.appointments

import com.projectx.fisioapp.repository.entitymodel.responses.GetAppointmentsResponse
import com.projectx.fisioapp.repository.entitymodel.responses.UpdateAppointmentResponse

fun convertAppointments(response: GetAppointmentsResponse): List<AppoinmentData> {
    val appointmentsList: MutableList<AppoinmentData> = arrayListOf()

    response.result.let{

        response.result!!.rows.let {

            val rows = response.result!!.rows
            rows.let {

                rows!!.map {
                    val name = it.customer!!.name + " " + it.customer!!.lastName
                    val appointment = AppoinmentData(
                            it.id!!,
                            it.service!!.databaseId,
                            it.service!!.price.toString(),
                            it.customer!!.id!!,
                            name,
                            it.address!!,
                            it.professional!!.id!!,
                            it.isConfirmed!!,
                            it.isCancelled!!,
                            it.date!!,
                            it.latitude.toString(),
                            it.longitude.toString(),
                            it.extraInfo!!
                    )
                    appointmentsList.add(appointment)
                }
            }
        }
    }
    return appointmentsList
}


fun converterDeleteAppointment(response: UpdateAppointmentResponse): Boolean {
    var appointmentDeleted = false


    response.ok.let {
        if (it == true){
            appointmentDeleted = it
        }
    }

    return appointmentDeleted

}
