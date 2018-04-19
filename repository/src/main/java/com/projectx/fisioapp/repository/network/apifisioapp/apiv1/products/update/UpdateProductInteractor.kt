package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.update

import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

internal interface UpdateProductInteractor {
    fun execute(token: String, item: CatalogData, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit)
}