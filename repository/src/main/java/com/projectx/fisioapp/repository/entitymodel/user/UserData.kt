package com.projectx.fisioapp.repository.entitymodel.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class UserData (
        @SerializedName("_id") var id: String,
        @Expose var name: String,
        @Expose var lastName: String,
        @Expose var email: String,
        @Expose var isProfessional: Boolean,
        @Expose var fellowshipNumber: String,
        @Expose var gender: String,
        @Expose var address: String,
        @Expose var phone: String,
        @Expose var birthDate: Date,
        @Expose var nationalId: String,
        @Expose var registrationDate: Date,
        @Expose var lastLoginDate: Date
)