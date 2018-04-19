package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.insert

import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

internal interface InsertServiceInteractor {
    fun execute(token: String, item: CatalogData, success: (successMessage: CatalogData) -> Unit, error: (errorMessage: String) -> Unit)
}