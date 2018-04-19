package com.projectx.fisioapp.domain.interactor.appointments

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.domain.model.util.AppointmentMapper
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import java.lang.ref.WeakReference

class UpdateAppointmentIntImpl(context: Context) : UpdateAppointmentInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get()!!)

    override fun execute(token: String, id: String, isConfirmed: Boolean, isCancelled: Boolean, success: SuccessCompletion<String>, error: ErrorCompletion) {

        repository.updateAppointment(token, id, isConfirmed, isCancelled,
                success = {
                    success.successCompletion("Successfully saved")
                }, error = {
            error.errorCompletion(it)
        })
    }

}