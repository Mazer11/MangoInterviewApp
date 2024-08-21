package org.interview.remote.repository.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.interview.remote.client.NetworkClient
import org.interview.remote.models.ErrorContent
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
    override suspend fun registration(request: RegistrationRequest): Response<RegistrationResponse?> =
        withContext(Dispatchers.Default) {
            try {
                val result = client.getApiService().registration(request)
                client.storeTokens(result.accessToken ?: "", result.refreshToken ?: "")
                Response.Success(result)
            } catch (e: Exception) {
                Response.Error(
                    message = ErrorContent(
                        msg = e.localizedMessage ?: "Ошибка при попытке регистрации.",
                        type = "",
                        loc = emptyList()
                    )
                )
            }
        }

    override suspend fun checkAuthCode(request: CheckAuthRequest): Response<CheckAuthResponse?> =
        withContext(Dispatchers.Default) {
            try {
                val result = client.getApiService().checkAuthCode(request)
                client.storeTokens(result.accessToken, result.refreshToken)
                Response.Success(result)
            } catch (e: Exception) {
                Response.Error(
                    message = ErrorContent(
                        msg = e.localizedMessage ?: "Ошибка при попытке регистрации.",
                        type = "",
                        loc = emptyList()
                    )
                )
            }
        }

    override suspend fun sendAuthCode(request: SendAuthRequest): Response<SendAuthResponse?> =
        withContext(Dispatchers.Default) {
            try {
                val result = client.getApiService().sendAuthCode(request)
                Response.Success(result)
            } catch (e: Exception) {
                Response.Error(
                    message = ErrorContent(
                        msg = e.localizedMessage ?: "Ошибка при попытке регистрации.",
                        type = "",
                        loc = emptyList()
                    )
                )
            }
        }

}