package com.sehan.stepforwater.model

import java.io.Serializable

data class PersonalInfo(
    val id: String,
    val name: String,
    val hashPw: String,
    val profile: String,
    val information: String,
    val settings: String,
    val salt: String,
    val fcm: String
) : Serializable