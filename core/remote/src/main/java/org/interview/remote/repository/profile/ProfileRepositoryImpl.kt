package org.interview.remote.repository.profile

import org.interview.remote.client.NetworkClient
import org.interview.remote.models.Response
import org.interview.remote.models.request.UpdateProfileRequest
import org.interview.remote.models.response.GetProfileResponse
import org.interview.remote.models.response.UpdateProfileResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val client: NetworkClient
) : ProfileRepository {
    override fun getProfileData(): Response<GetProfileResponse> =
        client.getApiService().getProfileData()

    override fun updateProfileData(request: UpdateProfileRequest): Response<UpdateProfileResponse> =
        client.getApiService().updateProfileData(request)
}