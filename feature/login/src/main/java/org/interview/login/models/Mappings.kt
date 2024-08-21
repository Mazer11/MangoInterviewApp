package org.interview.login.models

import org.interview.remote.models.request.CheckAuthRequest
import org.interview.remote.models.request.RegistrationRequest
import org.interview.remote.models.request.SendAuthRequest
import org.interview.remote.models.response.CheckAuthResponse
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.models.response.SendAuthResponse

//----------------------Data-to-Request--------------------------------

fun SendAuthData.toRequest() = SendAuthRequest(
    phone = phone
)

fun CheckAuthData.toRequest() = CheckAuthRequest(
    phone = phone,
    code = code
)

fun RegistrationData.toRequest() = RegistrationRequest(
    phone = phone,
    name = name,
    username = username
)

//----------------------Response-to-Result-----------------------------

fun SendAuthResponse.toResult() = SendAuthResult(
    isSuccess = isSuccess
)

fun CheckAuthResponse.toResult() = CheckAuthResult(
    userId = userId,
    isUserExists = isUserExists
)

fun RegistrationResponse.toResult() = RegistrationResult(
    userId = userId
)