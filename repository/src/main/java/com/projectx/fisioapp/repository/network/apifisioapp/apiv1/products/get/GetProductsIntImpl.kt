package com.projectx.fisioapp.repository.network.apifisioapp.apiv1.products.get

import android.util.Log

import com.projectx.fisioapp.repository.entitymodel.responses.GetCatalogResponse
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogType
import com.projectx.fisioapp.repository.entitymodel.catalog.converter.convertList
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppClient
import com.projectx.fisioapp.repository.network.apifisioapp.apiv1.APIV1FisioAppInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class GetProductsIntImpl(): GetProductsInteractor {
    override fun execute(token: String, success: (catalogItems: List<CatalogData>) -> Unit, error: (errorMessage: String) -> Unit) {

        val apiInterfaceLocalhost: APIV1FisioAppInterface =
                APIV1FisioAppClient.client.create(APIV1FisioAppInterface::class.java)

        /**
         * Get Products
         */
        val callGetProducts = apiInterfaceLocalhost.doGetProducts(token)
        callGetProducts.enqueue(object : Callback<GetCatalogResponse> {
            override fun onResponse(call: Call<GetCatalogResponse>, response: Response<GetCatalogResponse>) {
                val backResponse = response.body()

                backResponse.let { success(convertList(backResponse!!, CatalogType.PRODUCT)) }

            }

            override fun onFailure(call: Call<GetCatalogResponse>, t: Throwable?) {
                call.cancel()
                Log.d("App: ", t?.localizedMessage ?: "Connection to server not available")
                error(t?.localizedMessage ?: "Connection to server not available")
            }

        })
    }

}