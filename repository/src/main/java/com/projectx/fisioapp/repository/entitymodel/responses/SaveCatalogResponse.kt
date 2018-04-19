package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData

class SaveCatalogResponse {

    @Expose
    var ok: Boolean? = null
    @Expose
    var message: String? = null
    @Expose
    var result: CatalogData? = null

}