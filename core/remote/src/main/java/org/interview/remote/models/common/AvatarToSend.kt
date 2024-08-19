package org.interview.remote.models.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AvatarToSend(
    @Json(name = "filename") val filename: String,
    @Json(name = "base_64") val base64: String
)