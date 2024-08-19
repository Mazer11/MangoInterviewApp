package org.interview.remote.repository.auth

import org.interview.remote.client.NetworkClient
import org.interview.remote.models.Response
import org.interview.remote.models.request.CheckAuthRequest
import org.interview.remote.models.request.RegistrationRequest
import org.interview.remote.models.request.SendAuthRequest
import org.interview.remote.models.response.CheckAuthResponse
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.models.response.SendAuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val client: NetworkClient
) : AuthRepository {
    override fun registration(request: RegistrationRequest): Response<RegistrationResponse> =
        client.getApiService().registration(request)

    override fun checkAuthCode(request: CheckAuthRequest): Response<CheckAuthResponse> =
        client.getApiService().checkAuthCode(request)

    override fun sendAuthCode(request: SendAuthRequest): Response<SendAuthResponse> =
        client.getApiService().sendAuthCode(request)
}