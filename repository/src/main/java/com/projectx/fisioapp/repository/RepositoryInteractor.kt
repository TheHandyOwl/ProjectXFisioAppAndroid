package com.projectx.fisioapp.repository

import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData


interface RepositoryInteractor {

    /******** users ********/
    fun authenticateUser(email: String, password: String,
                         success: (token: String) -> Unit, error: (errorMessage: String) -> Unit)
    fun registerUser(name: String, email: String, password: String,
                     success: (ok: Boolean) -> Unit, error: (errorMessage: String) -> Unit)


    /******** catalog (products and services) ********/
    fun countCatalogItems(): Int
    fun deleteAllCatalogItems(success: () -> Unit, error: (errorMessage: String) -> Unit)
    fun getAllCatalogItems(forceUpdate: Boolean, token: String, type: String, success: (catalogList: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit)
    fun insertService(token: String, item: CatalogData, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit)
    fun deleteService(token: String, id: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit)

}
