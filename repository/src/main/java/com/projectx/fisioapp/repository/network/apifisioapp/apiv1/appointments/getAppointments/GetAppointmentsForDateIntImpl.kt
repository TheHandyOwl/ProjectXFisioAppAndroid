package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.getAppointments

import android.util.Log
import com.projectx.fisioapp.repository.entitymodel.appointments.AppoinmentData
import com.projectx.fisioapp.repository.entitymodel.appointments.convertAppointments
import com.projectx.fisioapp.repository.entitymodel.responses.GetAppointmentsResponse
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAppointmentsForDateIntImpl : GetAppointmentsForDateInteractor {
    override fun execute(token: String, date: String, success: (appointmentsList: List<AppoinmentData>) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        // val dateFrom = SimpleDateFormat("yyyy/MM/dd");
        val callGetAppointmentsForDate = apiInterfaceLocalhost.doGetAppointmentsForDate(token, date, date)
        callGetAppointmentsForDate.enqueue(object : Callback<GetAppointmentsResponse> {

            override fun onResponse(call: Call<GetAppointmentsResponse>, response: Response<GetAppointmentsResponse>) {
                val backResponse = response.body()
                backResponse.let { success(convertAppointments(backResponse!!)) }
            }

            override fun onFailure(call: Call<GetAppointmentsResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Connection to server not available")
                error(t?.localizedMessage ?: "Connection to server not available")
            }
        })
    }
}