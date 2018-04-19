package com.projectx.fisioapp.domain.interactor.users.authenticateuser

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User


interface AuthenticateUserInteractor {
    fun execute(email: String, password: String, success: (user: User, token: String) -> Unit, error: ErrorCompletion)
}