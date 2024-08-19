package org.interview.remote.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.interview.remote.models.common.AvatarToSend
import org.interview.remote.models.common.Avatars

@JsonClass(generateAdapter = true)
data class GetProfileResponse(
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "birthday") val birthday: String,
    @Json(name = "city") val city: String,
    @Json(name = "status") val status: String,
    @Json(name = "avatar") val avatar: AvatarToSend,
    @Json(name = "id") val id: Int,
    @Json(name = "phone") val phone: String,
    @Json(name = "avatars") val avatars: Avatars
)
