package com.projectx.fisioapp.repository.entitymodel.appointments

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultAppointments {

    @Expose
    var rows: List<AppoinmentRow>? = null

}