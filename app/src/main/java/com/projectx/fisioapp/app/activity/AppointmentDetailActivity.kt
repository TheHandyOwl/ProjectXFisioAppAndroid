package com.projectx.fisioapp.app.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.projectx.fisioapp.R
import com.projectx.fisioapp.app.fragment.AppointmentDetailFragment
import com.projectx.fisioapp.app.fragment.AppointmentDetailListener
import com.projectx.fisioapp.app.utils.toastIt
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.interactor.appointments.UpdateAppointmentIntImpl
import com.projectx.fisioapp.domain.interactor.appointments.UpdateAppointmentInteractor
import com.projectx.fisioapp.domain.model.Appointment
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class AppointmentDetailActivity : ParentActivity(), AppointmentDetailListener {

    companion object {
        private val EXTRA_APPOINTMENT = "EXTRA_APPOINTMENT"

        fun newInstance(context: Context, appointment: Appointment): Intent {
            val intent = Intent(context, AppointmentDetailActivity::class.java)
            intent.putExtra(EXTRA_APPOINTMENT, appointment)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_detail)

        val appointment = intent.getSerializableExtra(EXTRA_APPOINTMENT) as Appointment

        supportActionBar?.title = appointment.customerName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (fragmentManager.findFragmentById(R.id.appointment_detail_fragment) == null) {
            val fragment = AppointmentDetailFragment.newInstance(appointment)
            fragmentManager.beginTransaction()
                    .add(R.id.appointment_detail_fragment, fragment)
                    .commit()
        }

    }

    override fun onSavePressed(view: View, item: Appointment) {
        async(UI) {
            val updateAppointment: UpdateAppointmentInteractor = UpdateAppointmentIntImpl(view.context)

            try {
                updateAppointment.execute(
                        token,
                        item.id,
                        item.isConfirmed,
                        item.isCancelled,
                        success = object : SuccessCompletion<String> {
                            override fun successCompletion(e: String) {
                                toastIt(view.context, e)

                                val intent = Intent()
                                intent.putExtra("result", -1)
                                finalizeActivity(Activity.RESULT_OK, intent)
                            }
                        },
                        error = object : ErrorCompletion {
                            override fun errorCompletion(errorMessage: String) {
                                toastIt(view.context, errorMessage)
                                finalizeActivity(Activity.RESULT_CANCELED, Intent())
                            }
                        })
            } catch (e: Exception) {
                toastIt(view.context, "Error: " + e.localizedMessage )
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finalizeActivity(Activity.RESULT_CANCELED, Intent())
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
