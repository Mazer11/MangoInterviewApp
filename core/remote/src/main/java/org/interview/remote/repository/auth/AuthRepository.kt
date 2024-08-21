package org.interview.remote.repository.auth

import org.interview.remote.models.Response
import org.interview.remote.models.request.CheckAuthRequest
import org.interview.remote.models.request.RegistrationRequest
import org.interview.remote.models.request.SendAuthRequest
import org.interview.remote.models.response.CheckAuthResponse
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.models.response.SendAuthResponse

interface AuthRepository {

    suspend fun registration(request: RegistrationRequest): Response<RegistrationResponse?>

    suspend fun checkAuthCode(request: CheckAuthRequest): Response<CheckAuthResponse?>

    suspend fun sendAuthCode(request: SendAuthRequest): Response<SendAuthResponse?>

}