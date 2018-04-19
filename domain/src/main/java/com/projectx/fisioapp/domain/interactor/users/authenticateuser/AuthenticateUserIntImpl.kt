package com.projectx.fisioapp.domain.interactor.users.authenticateuser

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.model.User
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import com.projectx.fisioapp.repository.entitymodel.user.UserData
import java.lang.ref.WeakReference


class AuthenticateUserIntImpl (context: Context) :AuthenticateUserInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get() !!)

    override fun execute(email: String, password: String, success: (user: User, token: String) -> Unit, error: ErrorCompletion) {
        repository.authenticateUser(
                email, password,
                success = { user: UserData, token: String ->

                    val userData: User = entityMapper(user)
                        success(userData, token)
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