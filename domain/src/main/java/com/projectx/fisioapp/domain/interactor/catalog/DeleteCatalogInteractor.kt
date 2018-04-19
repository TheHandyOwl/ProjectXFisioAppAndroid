package com.projectx.fisioapp.domain.interactor.catalog

import com.projectx.fisioapp.domain.interactor.ErrorCompletion
import com.projectx.fisioapp.domain.interactor.SuccessCompletion

interface DeleteCatalogInteractor {
    fun execute(token: String, id: String, type: String, success: SuccessCompletion<String>, error: ErrorCompletion)
}