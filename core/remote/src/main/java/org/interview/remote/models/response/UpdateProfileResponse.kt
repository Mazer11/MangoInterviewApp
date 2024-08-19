package org.interview.remote.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.interview.remote.models.common.Avatars

@JsonClass(generateAdapter = true)
data class UpdateProfileResponse(
    @Json(name = "avatars")val avatars: Avatars
)