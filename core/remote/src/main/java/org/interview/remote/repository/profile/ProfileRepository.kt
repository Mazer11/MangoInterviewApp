package org.interview.remote.repository.profile

import org.interview.remote.models.Response
import org.interview.remote.models.request.UpdateProfileRequest
import org.interview.remote.models.response.GetProfileResponse
import org.interview.remote.models.response.UpdateProfileResponse

interface ProfileRepository {

    fun getProfileData(): Response<GetProfileResponse>

    fun updateProfileData(request: UpdateProfileRequest): Response<UpdateProfileResponse>

}