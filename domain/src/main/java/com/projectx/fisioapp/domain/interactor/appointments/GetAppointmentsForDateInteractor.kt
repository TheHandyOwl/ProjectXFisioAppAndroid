package com.projectx.fisioapp.domain.interactor.appointments

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Appointments

interface GetAppointmentsForDateInteractor {
    fun execute(token: String, date: String, success: SuccessCompletion<Appointments>, error: ErrorCompletion)

}
