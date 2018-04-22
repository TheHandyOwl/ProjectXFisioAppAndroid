package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.projectx.fisioapp.repository.entitymodel.user.UserData

class UpdateUserImageResponse {
    @Expose
    var ok: Boolean? = null
    @SerializedName("User")
    var user: UserData? = null
    @Expose
    var message: String? = null
}