package com.projectx.fisioapp.domain.interactor.users.getuser

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import java.lang.ref.WeakReference

class GetUserIntImpl(context: Context) : GetUserInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get() !!)

    override fun execute(token: String, id: String, success: (user: User) -> Unit, error: ErrorCompletion) {
        repository.getUser(
                token, id,
                success = {
                    val user: User = entityMapper(it)
                    success(user)
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

}