package org.interview.profile.models

data class UpdateProfileData(
    val name: String = "",
    val username: String = "",
    val birthday: String? = null,
    val city: String? = null,
    val status: String? = null,
    val avatar: String? = null,
    val avatarFileName: String? = null
)
