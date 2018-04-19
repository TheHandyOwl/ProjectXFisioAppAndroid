package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.getuser

import android.util.Log
import com.projectx.fisioapp.repository.entitymodel.responses.GetUserResponse
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetUserIntImpl(): GetUserInteractor {
    override fun execute(token: String, id: String, success: (user: UserData) -> Unit, error: (errorMessage: String) -> Unit) {
        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Get User
         */
        val callGetUser = apiInterfaceLocalhost.doGetUser(token, id)
        callGetUser.enqueue(object : Callback<GetUserResponse> {
            override fun onResponse(call: Call<GetUserResponse>, response: Response<GetUserResponse>) {
                val responseObject = response.body()
                val user = responseObject?.result as UserData

                response.let { success(user) }

            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Connection to server not available")
                error(t?.localizedMessage ?: "Connection to server not available")
            }

        })
    }
}