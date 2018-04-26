package com.projectx.fisioapp.app.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7Il9pZCI6IjVhOWYwNTRmNjAyZGQwZTU0MGM3MWJjNiIsImZlbGxvd3NoaXBOdW1iZXIiOjMzLCJnZW5kZXIiOiJtYWxlIiwibmFtZSI6ImFsYW4iLCJsYXN0TmFtZSI6ImNhc2FzIiwiZW1haWwiOiJmaXNpb0BpbnZhbGlkLmNvbSIsInBhc3N3b3JkIjoiZWY3OTdjODExOGYwMmRmYjY0OTYwN2RkNWQzZjhjNzYyMzA0OGM5YzA2M2Q1MzJjYzk1YzVlZDdhODk4YTY0ZiIsImFkZHJlc3MiOiJBdi4gRmVsaXBlIElJLCBzL24iLCJwaG9uZSI6IjYyNjYyNjYyNiIsImJpcnRoRGF0ZSI6IjE5NzgtMTItMzBUMDA6MDA6MDAuMDAwWiIsIm5hdGlvbmFsSWQiOiIxMjM0NTY3OFoiLCJyZWdpc3RyYXRpb25EYXRlIjoiMjAxOC0wMS0wMVQwMTowMTowMC4wMDBaIiwibGFzdExvZ2luRGF0ZSI6IjIwMTgtMDMtMDdUMTY6MDA6MDAuMDAwWiIsIl9fdiI6MCwiaW1nIjoiNWE5ZjA1NGY2MDJkZDBlNTQwYzcxYmM2LTQyLmpwZyIsImRlbGV0ZWQiOmZhbHNlLCJpc1Byb2Zlc3Npb25hbCI6dHJ1ZX0sImlhdCI6MTUyNDc2OTE1OCwiZXhwIjoxNTI0OTQxOTU4fQ.3tudJWXNWHQqAqXWan8Toerv1C-Er8l_cIlrQFnm8DY"

        if (!checkToken()) {
            Router.navigateFromCalendarActivityToLoginActivity(this)
        }

    }

    override fun onResume() {
        super.onResume()

        title = getString(R.string.calendar_title)

        addBottomBar(this)

        if(lastDate.isBlank() && checkToken()){

            val date = Date()
            lastDate = formatDate(date, true)
            getAppointmentsForDate(this, lastDate)
        }

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
