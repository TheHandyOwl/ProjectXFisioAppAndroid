package com.projectx.fisioapp.domain.interactor.users.updateuser

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User


interface UpdateUserInteractor {
    fun execute(token: String, user: User, success: (ok: Boolean, user: User) -> Unit, error: ErrorCompletion)
}