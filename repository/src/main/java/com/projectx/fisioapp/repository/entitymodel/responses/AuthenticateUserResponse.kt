package com.projectx.fisioapp.repository.entitymodel.responses

import com.google.gson.annotations.Expose
import com.projectx.fisioapp.repository.entitymodel.user.UserData


class AuthenticateUserResponse{
        @Expose var ok: Boolean? = null
        @Expose var result: Result? = null

        inner class Result {
                @Expose
                var user: UserData? = null
                @Expose
                var token: String? = null
        }
}
