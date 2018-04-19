package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.delete

internal interface DeleteServiceInteractor {
    fun execute(token: String, id: String, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit)
}