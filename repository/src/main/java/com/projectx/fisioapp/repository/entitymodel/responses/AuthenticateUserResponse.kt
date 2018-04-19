package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.projectx.fisioapp.repository.entitymodel.user.UserData


class AuthenticateUserResponse (
        @Expose
        var ok: Boolean?,
        @Expose
        var result: UserData?,
        @Expose
        var token: String?
)
