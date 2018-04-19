package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.getuser

import com.projectx.fisioapp.repository.entitymodel.user.UserData


interface GetUserInteractor {
    fun execute(token: String, id: String, success: (user: UserData) -> Unit, error: (errorMessage: String) -> Unit)
}