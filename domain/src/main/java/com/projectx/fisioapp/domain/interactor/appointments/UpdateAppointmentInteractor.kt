package com.projectx.fisioapp.domain.interactor.appointments

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion

interface UpdateAppointmentInteractor {
    fun execute(token: String, id: String, isConfirmed: Boolean, isCancelled: Boolean, success: SuccessCompletion<String>, error: ErrorCompletion)

}