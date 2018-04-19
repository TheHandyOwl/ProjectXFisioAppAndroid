package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.registeruser

import android.util.Log

import com.projectx.fisioapp.repository.entitymodel.responses.RegisterUserResponse
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class RegisterUserIntImpl (): RegisterUserInteractor {
    override fun execute(name: String, email: String, password: String, success: (ok: Boolean, msg: String) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Create new user
         */
        val callRegisterUser = apiInterfaceLocalhost.doRegisterUser(name, email, password)
        callRegisterUser.enqueue(object : Callback<RegisterUserResponse> {
            override fun onResponse(call: Call<RegisterUserResponse>, response: Response<RegisterUserResponse>) {
                val bodyResponse = response.body()
                val ok = bodyResponse?.ok ?: false
                val msg = bodyResponse?.message ?: "Error with user registration"
                success(ok, msg)
            }

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Conection to server not available")
                error(t?.localizedMessage ?: "Conection to server not available")
            }

        })
    }
}