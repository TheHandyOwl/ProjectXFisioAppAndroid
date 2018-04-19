package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.update

import com.projectx.fisioapp.repository.entitymodel.responses.UpdateUserResponse
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateUserIntImpl() : UpdateUserInteractor {
    override fun execute(token: String, user: UserData, success: (ok: Boolean, user: UserData) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Update User
         */
        val updateUser = apiInterfaceLocalhost.doUpdateUser( token, user.id,
                user.name, user.lastName, user.email, user.isProfessional, user.fellowshipNumber,
                user.gender, user.address, user.phone, user.birthDate, user.nationalId)
        updateUser.enqueue(object : Callback<UpdateUserResponse> {

            override fun onResponse(call: Call<UpdateUserResponse>, response: Response<UpdateUserResponse>) {
                val backResponse = response.body()
                val ok = backResponse?.ok ?: false
                val userData = backResponse?.result as UserData
                if (ok) success(ok, userData)
            }

            override fun onFailure(call: Call<UpdateUserResponse>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }

}