package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.projectx.fisioapp.repository.entitymodel.user.UserData


class GetUserResponse {
    @Expose
    var ok: Boolean? = null
    @Expose
    var result: UserData? = null
}