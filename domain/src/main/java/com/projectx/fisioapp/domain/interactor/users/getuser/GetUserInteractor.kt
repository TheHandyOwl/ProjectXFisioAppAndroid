package com.projectx.fisioapp.domain.interactor.users.getuser

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User


interface GetUserInteractor {
    fun execute(token: String, id: String, success: (user: User) -> Unit, error: ErrorCompletion)
}
