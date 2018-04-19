package com.projectx.fisioapp.domain.interactor.users.updateuser

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import java.lang.ref.WeakReference


class UpdateUserIntImpl(context: Context) : UpdateUserInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get() !!)

    override fun execute(token: String, user: User, success: (ok: Boolean, user: User) -> Unit, error: ErrorCompletion) {

        val userData = entityMapper(user)

        repository.updateUser(
                token, userData,
                success = { ok: Boolean, userData: UserData ->
                    val finalUser: User = entityMapper(userData)
                    success(ok, finalUser)
                }, error = {
                    error.errorCompletion(it)
                }
        )
    }

    private fun entityMapper(userData: UserData): User {
        return User(
                userData.id,
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

    private fun entityMapper(user: User): UserData {
        return UserData(
                user.id,
                user.name,
                user.lastName,
                user.email,
                user.isProfessional,
                user.fellowshipNumber,
                user.gender,
                user.address,
                user.phone,
                user.birthDate,
                user.nationalId,
                user.registrationDate,
                user.lastLoginDate
        )
    }

}