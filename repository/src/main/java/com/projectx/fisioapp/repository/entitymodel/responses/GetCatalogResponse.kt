package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.projectx.fisioapp.repository.entitymodel.catalog.ResultCatalogItems

class GetCatalogResponse {

    @Expose
    var ok: Boolean? = null
    @Expose
    var result: ResultCatalogItems? = null

}