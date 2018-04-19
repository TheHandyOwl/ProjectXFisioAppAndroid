package com.projectx.fisioapp.app.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.fragment.AppointmentsListFragment
import com.projectx.fisioapp.app.fragment.CalendarFragment
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.utils.RQ_OPERATION
import com.projectx.fisioapp.app.utils.formatDate
import com.projectx.fisioapp.app.utils.toastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.interactor.appointments.GetAppointmentsForDateIntImpl
import com.projectx.fisioapp.domain.interactor.appointments.GetAppointmentsForDateInteractor
import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.domain.model.Appointments
import kotlinx.android.synthetic.main.appointment_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class CalendarActivity : ParentActivity(),
        AppointmentsListFragment.OnSelectedAppointmentListener,
        CalendarFragment.OnSelectedDateListener {


    private lateinit var calendarFragment: CalendarFragment
    lateinit var appointmentsListFragment: AppointmentsListFragment
    private var lastDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        if (!checkToken()) {
            Router.navigateFromCalendarActivityToLoginActivity(this)
        }

        title = getString(R.string.calendar_title)

        addBottomBar(this)

        if(lastDate.isBlank()){

            val date = Date()
            lastDate = formatDate(date, true)
            getAppointmentsForDate(this, lastDate)
        }

    }

    override fun onResume() {
        super.onResume()

        calendarFragment = fragmentManager.findFragmentById(R.id.calendar_fragment) as CalendarFragment
        appointmentsListFragment = fragmentManager.findFragmentById(R.id.appointments_fragment) as AppointmentsListFragment
        appointmentsListFragment.setParent(this)
    }

    private fun getAppointmentsForDate(context: Context, date: String) {
        async(UI){

            val getAppointmentsForDate: GetAppointmentsForDateInteractor = GetAppointmentsForDateIntImpl(context)
            try{
                lastDate = date
                getAppointmentsForDate.execute(token, date,
                        success = object : SuccessCompletion<Appointments> {
                            override fun successCompletion(e: Appointments) {
                                appointmentsListFragment.setAppointmentsList(e.appointments)
                                appointmentsListFragment.setupRecyclerView(appointment_list, e)
                            }
                        }, error = object : ErrorCompletion {
                    override fun errorCompletion(errorMessage: String) {
                        toastIt(baseContext, errorMessage)
                    }
                })
            } catch (e: Exception) {
                toastIt(context, "Error: " + e.localizedMessage)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RQ_OPERATION && resultCode == RESULT_OK) {
            getAppointmentsForDate(this, lastDate)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statusbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.checkOptionSelected(item)
    }


    // ***** Fragment AppointmentsList listener *****
    override fun onSelectedAppointment(appointment: Appointment) {
        Router.navigateFromCalendarActivityToAppointmentDetailActivity(this, appointment)
    }

    // ***** CalendarFragment listener *****
    override fun onSelectedDate(date: String) {
        getAppointmentsForDate(this, date)
    }

}
