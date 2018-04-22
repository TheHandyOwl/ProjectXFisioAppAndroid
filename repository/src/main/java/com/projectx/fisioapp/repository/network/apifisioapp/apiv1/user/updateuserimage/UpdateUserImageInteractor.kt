package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.user.updateuserimage

import android.graphics.drawable.Drawable
import com.projectx.fisioapp.repository.entitymodel.user.UserData


interface UpdateUserImageInteractor {
    fun execute(token: String, id: String, image: Drawable, success: (ok: Boolean, user: UserData, message: String) -> Unit, error: (errorMessage: String) -> Unit)
}