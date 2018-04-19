package com.projectx.fisioapp.domain.interactor.appointments

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Appointment

interface UpdateAppointmentInteractor {
    fun execute(token: String, id: String, isConfirmed: Boolean, isCancelled: Boolean, success: SuccessCompletion<String>, error: ErrorCompletion)

}