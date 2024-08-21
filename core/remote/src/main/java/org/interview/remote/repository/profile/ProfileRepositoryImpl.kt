package org.interview.remote.repository.profile

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.interview.remote.client.NetworkClient
import org.interview.remote.models.ErrorContent
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
    override suspend fun getProfileData(): Response<GetProfileResponse?> =
        withContext(Dispatchers.Default) {
            try {
                val result = client.getApiService().getProfileData()

                Response.Success(result)
            } catch (e: Exception) {
                Response.Error(
                    message = ErrorContent(
                        msg = e.message ?: "",
                        type = "",
                        loc = emptyList()
                    )
                )
            }
        }

    override suspend fun updateProfileData(request: UpdateProfileRequest): Response<UpdateProfileResponse?> =
        withContext(Dispatchers.Default) {
            try {
                val result = client.getApiService().updateProfileData(request)
                Response.Success(result)
            } catch (e: Exception) {
                Response.Error(
                    message = ErrorContent(
                        msg = e.message ?: "",
                        type = "",
                        loc = emptyList()
                    )
                )
            }
        }
}