package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.get

import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

internal interface GetServicesInteractor {
    fun execute(token: String, success: (catalogItems: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit)
}