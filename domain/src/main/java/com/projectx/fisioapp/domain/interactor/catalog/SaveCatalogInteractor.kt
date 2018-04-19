package com.projectx.fisioapp.domain.interactor.catalog

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion
import com.projectx.fisioapp.domain.model.Catalog

interface SaveCatalogInteractor {
    fun execute(token: String, item: Catalog, success: SuccessCompletion<String>, error: ErrorCompletion)
}