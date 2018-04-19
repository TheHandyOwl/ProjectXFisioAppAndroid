package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.appointments.updateAppointment

import android.util.Log
import com.projectx.fisioapp.repository.entitymodel.appointments.*
import com.projectx.fisioapp.repository.entitymodel.responses.UpdateAppointmentResponse
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class UpdateAppointmentIntImpl(): UpdateAppointmentInteractor {
    override fun execute(token: String, id: String, isConfirmed: Boolean, isCancelled: Boolean, success: (result: Boolean) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        val confirmAppointment = apiInterfaceLocalhost.doUpdateAppointment(token, id, isConfirmed, isCancelled)
        confirmAppointment.enqueue(object : Callback<UpdateAppointmentResponse> {

            override fun onResponse(call: Call<UpdateAppointmentResponse>?, response: Response<UpdateAppointmentResponse>) {
                val backResponse = response.body()

                backResponse.let {
                    success(converterDeleteAppointment(backResponse!!))
                }
            }

            override fun onFailure(call: Call<UpdateAppointmentResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Connection to server not available")
                error(t?.localizedMessage ?: "Connection to server not available")
            }

        })
    }
}