package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose

class DeleteCatalogResponse {

    @Expose
    var ok: Boolean? = null
    @Expose
    var result: String? = null

}