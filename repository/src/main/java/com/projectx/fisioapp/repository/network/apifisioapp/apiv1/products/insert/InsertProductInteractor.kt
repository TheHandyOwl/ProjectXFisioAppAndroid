package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.insert

import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

internal interface InsertProductInteractor {
    fun execute(token: String, item: CatalogData, success: (successMessage: CatalogData) -> Unit, error: (errorMessage: String) -> Unit)
}