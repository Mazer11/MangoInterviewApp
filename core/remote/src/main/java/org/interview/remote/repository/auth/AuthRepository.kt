package org.interview.remote.repository.auth

import org.interview.remote.models.Response
import org.interview.remote.models.request.CheckAuthRequest
import org.interview.remote.models.request.RegistrationRequest
import org.interview.remote.models.request.SendAuthRequest
import org.interview.remote.models.response.CheckAuthResponse
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.models.response.SendAuthResponse

interface AuthRepository {

    fun registration(request: RegistrationRequest): Response<RegistrationResponse>

    fun checkAuthCode(request: CheckAuthRequest): Response<CheckAuthResponse>

    fun sendAuthCode(request: SendAuthRequest): Response<SendAuthResponse>

}