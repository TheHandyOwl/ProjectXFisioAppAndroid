package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.services.insert

import android.util.Log
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

import com.projectx.fisioapp.repository.entitymodel.responses.SaveCatalogResponse
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class InsertServiceIntImpl: InsertServiceInteractor {
    override fun execute(token: String, item: CatalogData, success: (successMessage: String) -> Unit, error: (errorMessage: String) -> Unit) {

        var apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Insert Service
         */
        val insertService = apiInterfaceLocalhost.doInsertService(token, item.name, "5a9f054f602dd0e540c71bc6", item.description, item.price)
        insertService.enqueue(object : Callback<SaveCatalogResponse> {
            override fun onResponse(call: Call<SaveCatalogResponse>, response: Response<SaveCatalogResponse>) {
                val response = response.body()
                if (response !== null) response.result.let { success(response.result!!) }
            }

            override fun onFailure(call: Call<SaveCatalogResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Connection to server not available")
                error(t?.localizedMessage ?: "Connection to server not available")
            }

        })
    }

}