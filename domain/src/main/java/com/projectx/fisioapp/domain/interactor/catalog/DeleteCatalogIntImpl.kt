package com.projectx.fisioapp.domain.interactor.catalog

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import java.lang.ref.WeakReference

class DeleteCatalogIntImpl(context: Context) : DeleteCatalogInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get()!!)

    override fun execute(token: String, id: String, type: String, success: SuccessCompletion<String>, error: ErrorCompletion) {

        repository.deleteCatalogData(token, id, type,
                success = {
                    success.successCompletion("Deleted correctly")
                }, error = {
                    error.errorCompletion(it)
                })

    }

}