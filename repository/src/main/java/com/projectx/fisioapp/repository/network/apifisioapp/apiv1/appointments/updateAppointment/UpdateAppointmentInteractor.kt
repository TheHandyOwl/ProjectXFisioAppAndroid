package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.updateAppointment

internal  interface UpdateAppointmentInteractor {
    fun execute(token: String, id: String, isConfirmed: Boolean, isCancelled: Boolean, success: (result: Boolean) -> Unit, error: (errorMessage: String) -> Unit)
}