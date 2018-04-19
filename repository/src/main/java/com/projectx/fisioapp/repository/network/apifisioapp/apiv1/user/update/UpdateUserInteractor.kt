package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.update

import com.projectx.fisioapp.repository.entitymodel.user.UserData


interface UpdateUserInteractor {
    fun execute(token: String, user: UserData, success: (ok: Boolean, user: UserData) -> Unit, error: (errorMessage: String) -> Unit)
}