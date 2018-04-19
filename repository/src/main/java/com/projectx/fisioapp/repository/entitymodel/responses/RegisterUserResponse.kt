package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose


class RegisterUserResponse {
    @Expose
    var ok: Boolean? = false
    @Expose
    var message: String? = ""
}
