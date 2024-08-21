package org.interview.remote.models.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.interview.remote.models.common.AvatarToSend

@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    @Json(name = "name") val name: String = "",
    @Json(name = "username") val username: String = "",
    @Json(name = "birthday") val birthday: String? = null,
    @Json(name = "city") val city: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "avatar") val avatar: AvatarToSend? = null
)
