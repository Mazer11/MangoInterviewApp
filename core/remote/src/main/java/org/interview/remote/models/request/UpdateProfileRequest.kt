package org.interview.remote.models.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.interview.remote.models.common.AvatarToSend

@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "birthday") val birthday: String,
    @Json(name = "city") val city: String,
    @Json(name = "status") val status: String,
    @Json(name = "avatar") val avatar: AvatarToSend
)
