package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.deleteAppointment

internal  interface DeleteAppointmentInteractor {
    fun execute(token: String, id: String, success: (result: Boolean) -> Unit, error: (errorMessage: String) -> Unit)
}
