package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.registeruser

internal interface RegisterUserInteractor {
    fun execute(name: String, email: String, password: String, success: (ok: Boolean, msg: String) -> Unit, error: (errorMessage: String) -> Unit)
}