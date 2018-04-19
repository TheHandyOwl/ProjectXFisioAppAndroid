package com.projectx.fisioapp.domain.interactor.catalog

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Catalogs

interface GetCatalogListInteractor {
    fun execute(forceUpdate: Boolean, token: String, type: String, success: SuccessCompletion<Catalogs>, error: ErrorCompletion)
}