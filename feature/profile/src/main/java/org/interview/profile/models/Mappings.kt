package org.interview.profile.models

import org.interview.remote.models.common.AvatarToSend
import org.interview.remote.models.request.UpdateProfileRequest
import org.interview.remote.models.response.GetProfileResponse
import org.interview.remote.models.response.UpdateProfileResponse

fun GetProfileResponse.toResult(): GetProfileResult = GetProfileResult(
    name = profileData.name,
    username = profileData.username,
    birthday = profileData.birthday,
    city = profileData.city,
    status = profileData.status,
    avatar = profileData.avatar,
    avatars = profileData.avatars,
    id = profileData.id,
    phone = profileData.phone
)

fun GetProfileResult.toProfile(): ProfileData = ProfileData(
    name = name,
    username = username,
    birthday = birthday,
    city = city,
    status = status,
    avatar = avatar,
    id = id,
    phone = phone
)

fun GetProfileResult.toUpdateProfileData(): UpdateProfileData = UpdateProfileData(
    name = name,
    username = username,
    birthday = birthday,
    city = city,
    status = status,
    avatar = avatar
)

fun UpdateProfileResponse.toResult(): UpdateProfileResult = UpdateProfileResult(
    avatars = avatars
)

fun UpdateProfileData.toRequest(): UpdateProfileRequest = UpdateProfileRequest(
    name = name,
    username = username,
    birthday = birthday,
    city = city,
    status = status,
    avatar = AvatarToSend(
        filename = avatarFileName ?: "",
        base64 = avatar ?: ""
    )
)