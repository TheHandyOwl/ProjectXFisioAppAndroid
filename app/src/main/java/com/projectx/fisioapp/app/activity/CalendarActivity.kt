package com.projectx.fisioapp.app.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.fragment.*
import com.projectx.fisioapp.app.helper.BottomNavigationViewHelper
import com.projectx.fisioapp.app.router.Router
import com.projectx.fisioapp.app.utils.ToastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.interactor.appointments.GetAppointmentsForDateIntImpl
import com.projectx.fisioapp.domain.interactor.appointments.GetAppointmentsForDateInteractor
import com.projectx.fisioapp.domain.model.Appointment
import com.projectx.fisioapp.domain.model.Appointments
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class CalendarActivity : ParentActivity(),
        AppointmentsListFragment.OnSelectedAppointmentListener,
        CalendarFragment.OnSelectedDateListener {

    //private var list: Appointments? = null
    lateinit var calendarFragment: CalendarFragment
    lateinit var appointmentsListFragment: AppointmentsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (!checkToken()) {
            Router().navigateFromCalendarActivityToLoginActivity(this)
        }

    }

    override fun onResume() {
        super.onResume()

        calendarFragment = fragmentManager.findFragmentById(R.id.calendar_fragment) as CalendarFragment
        appointmentsListFragment = fragmentManager.findFragmentById(R.id.appointments_fragment) as AppointmentsListFragment
    }

    private fun getAppointmentsForDate(context: Context, date: String) {
        async(UI){

            val getAppointmentsForDate: GetAppointmentsForDateInteractor = GetAppointmentsForDateIntImpl(context)
            try{
                getAppointmentsForDate.execute(token, date,
                        success = object : SuccessCompletion<Appointments> {
                            override fun successCompletion(e: Appointments) {
                                //list = e
                                appointmentsListFragment.setAppointmentsList(e)
                            }
                        }, error = object : ErrorCompletion {
                    override fun errorCompletion(errorMessage: String) {
                        ToastIt(baseContext, "$errorMessage")
                    }
                })
            } catch (e: Exception) {
                ToastIt(context, "Error: " + e.localizedMessage)
            }
        }
    }


    // ***** Back button enabled *****
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // ***** Fragment AppointmentsList listener *****
    override fun onSelectedAppointment(appointment: Appointment) {
        Router().navigateFromCalendarActivityToAppointmentDetailActivity(this, appointment)

        /*val fragment = AppointmentDetailFragment.newInstance()
        fragmentManager.beginTransaction()
                .replace(R.id.appointments_fragment, fragment)
                .commit()*/

        /*if(resources.getBoolean(R.bool.screen_not_sw600) == false){
            Router().navigateFromCalendarActivityToAppointmentDetailActivity(this)
        } else if(resources.getBoolean(R.bool.screen_is_sw600) == false){
            val fragment = AppointmentDetailFragment.newInstance()
            fragmentManager.beginTransaction()
                    .add(R.id.appointments_fragment, fragment)
                    .commit()
        }*/
    }


    // ***** CalendarFragment listener *****
    override fun onSelectedDate(date: String) {
        getAppointmentsForDate(this, date)
    }

}
