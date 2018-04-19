package com.projectx.fisioapp.domain.interactor.catalog

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Catalog
import com.projectx.fisioapp.domain.model.util.Mapper
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import java.lang.ref.WeakReference

class SaveCatalogIntImpl(context: Context) : SaveCatalogInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get()!!)

    override fun execute(token: String, item: Catalog, success: SuccessCompletion<String>, error: ErrorCompletion) {

        repository.saveCatalogData(token, Mapper().mapCatalogToBenefit(item),
                success = {
                    success.successCompletion("Successfully saved")
                }, error = {
                    error.errorCompletion(it)
                })

    }

}