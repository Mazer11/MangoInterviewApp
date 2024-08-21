package org.interview.remote.repository.profile

import org.interview.remote.models.Response
import org.interview.remote.models.request.UpdateProfileRequest
import org.interview.remote.models.response.GetProfileResponse
import org.interview.remote.models.response.UpdateProfileResponse

interface ProfileRepository {

    suspend fun getProfileData(): Response<GetProfileResponse?>

    suspend fun updateProfileData(request: UpdateProfileRequest): Response<UpdateProfileResponse?>

}