package com.projectx.fisioapp.domain.interactor.users.updateuserimage

import android.content.Context
import android.graphics.drawable.Drawable
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import java.lang.ref.WeakReference


class UpdateUserImageIntImpl(context: Context): UpdateUserImageInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get() !!)

    override fun execute(token: String, id: String, image: Drawable, success: (ok: Boolean, user: User, message: String) -> Unit, error: ErrorCompletion) {

        repository.updateUserImage(
                token, id, image,
                success = { ok: Boolean, userData: UserData, message: String ->
                    val finalUser: User = entityMapper(userData)
                    success(ok, finalUser, message)
                }, error = {
                    error.errorCompletion(it)
                }
        )
    }

    private fun entityMapper(userData: UserData): User {
        return User(
                userData.id,
                userData.img,
                userData.name,
                userData.lastName,
                userData.email,
                userData.isProfessional,
                userData.fellowshipNumber,
                userData.gender,
                userData.address,
                userData.phone,
                userData.birthDate,
                userData.nationalId,
                userData.registrationDate,
                userData.lastLoginDate
        )
    }

}