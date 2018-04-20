package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.authenticateuser

import android.util.Log
import com.google.gson.Gson
import com.projectx.fisioapp.repository.entitymodel.responses.AuthenticateUserResponse
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.FileReader

internal class AuthenticateUserIntImpl (): AuthenticateUserInteractor {
    override fun execute(email: String, password: String, success: (user: UserData, token: String) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Authenticate existing user
         */
        val callGetToken = apiInterfaceLocalhost.doGetToken(email, password)
        callGetToken.enqueue(object : Callback<AuthenticateUserResponse> {
            override fun onResponse(call: Call<AuthenticateUserResponse>, response: Response<AuthenticateUserResponse>) {
                response.body().let {
                    val backResponse = response.body()
                    val user = backResponse?.result?.user as UserData
                    val token = backResponse?.result?.token as String
                    backResponse.let { success(user, token ) }
                }
            }

            override fun onFailure(call: Call<AuthenticateUserResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Connection to server not available")
                error(t?.localizedMessage ?: "Conection to server not available")
            }

        })
    }
}