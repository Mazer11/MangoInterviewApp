package org.interview.remote.models.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Avatars(
    @Json(name = "avatar") val avatar: String = "",
    @Json(name = "bigAvatar") val bigAvatar: String = "",
    @Json(name = "miniAvatar") val miniAvatar: String = ""
)