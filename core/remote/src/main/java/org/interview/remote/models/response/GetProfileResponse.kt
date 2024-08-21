package org.interview.remote.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.interview.remote.models.common.Avatars

@JsonClass(generateAdapter = true)
data class GetProfileResponse(
    @Json(name = "profile_data") val profileData: ProfileData
)

@JsonClass(generateAdapter = true)
data class ProfileData(
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String,
    @Json(name = "birthday") val birthday: String? = null,
    @Json(name = "city") val city: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "avatar") val avatar: String? = null,
    @Json(name = "id") val id: Int = -1,
    @Json(name = "phone") val phone: String,
    @Json(name = "avatars") val avatars: Avatars? = null
)