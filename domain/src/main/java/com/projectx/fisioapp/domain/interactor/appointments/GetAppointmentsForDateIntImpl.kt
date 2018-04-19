package com.projectx.fisioapp.domain.interactor.appointments

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Appointments
import com.projectx.fisioapp.domain.model.util.AppointmentMapper
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import java.lang.ref.WeakReference

class GetAppointmentsForDateIntImpl(context: Context) : GetAppointmentsForDateInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get()!!)

    override fun execute(token: String, date: String, success: SuccessCompletion<Appointments>, error: ErrorCompletion) {

        repository.getAppointmentsForDate(token, date,
                success = {
                    val appointments: Appointments = AppointmentMapper().appointmentsListMapper(it)
                    success.successCompletion(appointments)
                }, error = {
                    error.errorCompletion(it)
        })
    }
}