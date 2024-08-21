package org.interview.profile.models

import org.interview.remote.models.common.Avatars

data class GetProfileResult(
    val name: String,
    val username: String,
    val birthday: String? = null,
    val city: String? = null,
    val status: String? = null,
    val avatar: String? = null,
    val id: Int = -1,
    val phone: String,
    val avatars: Avatars? = null
)
