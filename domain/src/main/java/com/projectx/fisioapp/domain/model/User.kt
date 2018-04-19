package com.projectx.fisioapp.domain.model

import java.io.Serializable
import java.util.*

class User(var id: String,
        var name: String,
        var lastName: String,
        var email: String,
        var isProfessional: Boolean,
        var fellowshipNumber: String,
        var gender: String,
        var address: String,
        var phone: String,
        var birthDate: Date,
        var nationalId: String,
        var registrationDate: Date,
        var lastLoginDate: Date): Serializable
