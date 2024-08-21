package org.interview.profile.models

import org.interview.remote.models.common.Avatars

data class UpdateProfileResult(
    val avatars: Avatars? = null
)
