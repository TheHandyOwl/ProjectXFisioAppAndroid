package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.projectx.fisioapp.repository.entitymodel.appointments.ResultAppointments

class GetAppointmentsResponse{

    @Expose
    var ok: Boolean? = null
    @Expose
    var result: ResultAppointments? = null
}
