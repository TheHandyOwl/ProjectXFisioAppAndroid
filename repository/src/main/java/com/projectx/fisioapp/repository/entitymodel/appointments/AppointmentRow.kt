package com.projectx.fisioapp.repository.entitymodel.appointments

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.projectx.fisioapp.repository.entitymodel.catalog.CatalogData
import com.projectx.fisioapp.repository.entitymodel.common.Person
import java.util.*

class AppoinmentRow{
    @SerializedName("_id")
    var id: String? = null
    @Expose
    var service: CatalogData? = null
    @Expose
    var customer: Person? = null
    @Expose
    var professional: Person? = null
    @Expose
    var date: Date? = null
    @Expose
    var latitude: Double? = null
    @Expose
    var longitude: Double? = null
    @Expose
    var address: String? = null
    @Expose
    var extraInfo: String? = null
    @SerializedName("__v")
    var v: Int? = null
    @Expose
    var deleted: Boolean? = null
    @Expose
    var isCancelled: Boolean? = null
    @Expose
    var isConfirmed: Boolean? = null

}
