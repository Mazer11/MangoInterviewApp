package org.interview.remote.models.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendAuthRequest(
    @Json(name = "phone") val phone: String
)
