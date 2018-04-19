package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.authenticateuser

import com.projectx.fisioapp.repository.entitymodel.responses.AuthenticateUserResponse
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                val responseObject = response.body()
                val user = responseObject?.result as UserData
                val token = responseObject?.token as String
                success(user, token)
            }

            override fun onFailure(call: Call<AuthenticateUserResponse>, t: Throwable?) {
                call.cancel()
                error(t?.localizedMessage ?: "Conection to server not available")
            }

        })
    }
}