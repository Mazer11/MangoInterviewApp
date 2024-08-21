package org.interview.remote.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendAuthResponse(
    @Json(name = "is_success") val isSuccess: Boolean = false
)
