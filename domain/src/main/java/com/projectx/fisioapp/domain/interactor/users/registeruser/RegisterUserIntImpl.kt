package com.projectx.fisioapp.domain.interactor.users.registeruser

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import java.lang.ref.WeakReference


class RegisterUserIntImpl(context: Context) : RegisterUserInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get() !!)

    override fun execute(name: String, email: String, password: String, success: (ok: Boolean, msg: String) -> Unit, error: ErrorCompletion) {
        repository.registerUser(
                name, email, password,
                success = { ok: Boolean, msg: String ->
                    success(ok, msg)
                }, error = {
                    error.errorCompletion(it)
                }
        )
    }

}