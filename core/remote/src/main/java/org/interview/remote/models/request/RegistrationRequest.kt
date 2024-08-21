package org.interview.remote.models.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationRequest(
    @Json(name = "phone") val phone: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "username") val username: String = ""
)
