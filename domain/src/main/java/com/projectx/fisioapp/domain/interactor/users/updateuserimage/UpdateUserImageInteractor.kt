package com.projectx.fisioapp.domain.interactor.users.updateuserimage

import android.graphics.drawable.Drawable
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User


interface UpdateUserImageInteractor {
    fun execute(token: String, id: String, image: Drawable, success: (ok: Boolean, user: User, message: String) -> Unit, error: ErrorCompletion)
}