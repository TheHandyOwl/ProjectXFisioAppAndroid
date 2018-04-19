package com.projectx.fisioapp.repository.entitymodel.catalog

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.projectx.fisioapp.repository.entitymodel.common.Person

class Row {

    @SerializedName("_id")
    var id: String? = null
    @Expose
    var professional: Person? = null
    @Expose
    var name: String? = null
    @Expose
    var description: String? = null
    @Expose
    var price: Int? = null
    @SerializedName("__v")
    var v: Int? = null
    @Expose
    var deleted: Boolean? = null
    @Expose
    var isActive: Boolean? = null
    @Expose
    var image: String? = null

}