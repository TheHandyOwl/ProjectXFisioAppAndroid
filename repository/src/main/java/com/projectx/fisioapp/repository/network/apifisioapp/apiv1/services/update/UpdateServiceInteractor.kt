package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.update

import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

internal interface UpdateServiceInteractor {
    fun execute(token: String, item: CatalogData, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit)
}