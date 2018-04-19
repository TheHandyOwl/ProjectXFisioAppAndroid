package com.projectx.fisioapp.domain.interactor.users.registeruser

import com.projectx.fisioapp.domain.interactor.ErrorCompletion


interface RegisterUserInteractor {
    fun execute(name: String, email: String, password: String, success: (ok: Boolean, msg: String) -> Unit, error: ErrorCompletion)
}