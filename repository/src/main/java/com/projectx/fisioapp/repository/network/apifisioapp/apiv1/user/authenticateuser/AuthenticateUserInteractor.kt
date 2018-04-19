package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.authenticateuser

import com.projectx.fisioapp.repository.entitymodel.user.UserData


internal interface AuthenticateUserInteractor {
    fun execute(email: String, password: String, success: (user: UserData, token: String) -> Unit, error: (errorMessage: String) -> Unit)
}