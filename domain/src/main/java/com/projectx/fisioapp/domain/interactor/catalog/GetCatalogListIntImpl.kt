package com.projectx.fisioapp.domain.interactor.catalog

import android.content.Context
import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Catalogs
import com.projectx.fisioapp.domain.model.util.BenefitType
import com.projectx.fisioapp.domain.model.util.Mapper
import com.projectx.fisioapp.repository.RepositoryIntImpl
import com.projectx.fisioapp.repository.RepositoryInteractor
import java.lang.ref.WeakReference

class GetCatalogListIntImpl(context: Context) : GetCatalogListInteractor {

    private val weakContext = WeakReference<Context>(context)
    private val repository: RepositoryInteractor = RepositoryIntImpl(weakContext.get()!!)

    override fun execute(forceUpdate: Boolean, token: String, type: String, success: SuccessCompletion<Catalogs>, error: ErrorCompletion) {

        repository.getCatalogItems(forceUpdate, token, type,
                success = {
                    val items: Catalogs = Mapper().benefitsMapper(it, BenefitType.SERVICE)
                    success.successCompletion(items)
                }, error = {
                    error.errorCompletion(it)
                })

    }

}