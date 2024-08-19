package org.interview.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorContent(
    @Json(name = "msg") val msg: String,
    @Json(name = "type") val type: String,
    @Json(name = "loc") val loc: List<String>
)
