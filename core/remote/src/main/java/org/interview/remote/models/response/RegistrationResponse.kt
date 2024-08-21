package org.interview.remote.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegistrationResponse(
    @Json(name = "refresh_token") val refreshToken: String? = "",
    @Json(name = "access_token") val accessToken: String? = "",
    @Json(name = "user_id") val userId: Int = -1
)
