package org.interview.remote.client

import com.squareup.moshi.Moshi
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import org.interview.remote.NetworkAPI
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.repository.settings.SettingRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkClient @Inject constructor(
    settingRepository: SettingRepository
) {

    private val baseUrl = "https://plannerok.ru/"
    private val moshi: Moshi = Moshi.Builder().build()
    private val _accessToken = settingRepository.getAccessToken()

    private val tokenRefreshClient = OkHttpClient.Builder().apply {
        //TODO: check if debug
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }.build()

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        //TODO: check if debug
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        addInterceptor(AccessInterceptor(_accessToken.value))
    }
        .authenticator(TokenAuthenticator(tokenRefreshClient, moshi, settingRepository))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val apiService = retrofit.create(NetworkAPI::class.java)

    fun getApiService() = apiService

    private class AccessInterceptor(
        private val accessToken: String?
    ) : Interceptor {
        override fun intercept(chain: Chain): Response {
            val request = chain.request()

            val newRequest = accessToken?.let {
                request.newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            } ?: request

            return chain.proceed(newRequest)
        }
    }

    private class TokenAuthenticator(
        private val tokenRefreshClient: OkHttpClient,
        private val moshi: Moshi,
        private val settingRepository: SettingRepository
    ) : Authenticator {

        private var tokenRefreshInProgress: AtomicBoolean = AtomicBoolean(false)
        private var request: Request? = null

        private val _accessToken = settingRepository.getAccessToken()
        private val _refreshToken = settingRepository.getRefreshToken()

        override fun authenticate(route: Route?, response: Response): Request? {
            request = null

            if (!tokenRefreshInProgress.get()) {
                tokenRefreshInProgress.set(true)
                refreshToken()
                request = buildRequest(response.request.newBuilder())
                tokenRefreshInProgress.set(false)
            }

            // return null to stop retrying once responseCount returns 3 or above.
            return if (responseCount(response) >= 3) {
                null
            } else request
        }

        private fun refreshToken() {

            val requestBody =
                "{ \"refresh_token\": \"${_refreshToken.value}\" }".toRequestBody("application/json".toMediaType())
            val refreshRequest = Request.Builder()
                .url("https://plannerok.ru/api/v1/users/refresh-token/")
                .post(requestBody)
                .build()

            val response = tokenRefreshClient.newCall(refreshRequest).execute()
            val body = response.body?.string()

            if (response.isSuccessful) {
                if (!body.isNullOrEmpty()) {
                    val adapter = moshi.adapter(RegistrationResponse::class.java)
                    val result = adapter.fromJson(body)
                    if (result != null)
                        settingRepository.saveClientTokens(result.accessToken)
                } else {
                    //RESPONSE IS EMPTY
                }
            } else {
                //BAD REQUEST
            }

        }

        private fun responseCount(response: Response?): Int {
            var result = 1
            while (response?.priorResponse != null && result <= 3) {
                result++
            }
            return result
        }

        // Build a new request with new access token
        private fun buildRequest(requestBuilder: Request.Builder): Request {
            return requestBuilder
                .header(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
                .header(HEADER_AUTHORIZATION, HEADER_AUTHORIZATION_TYPE + _accessToken.value)
                .build()
        }

        companion object {
            const val HEADER_AUTHORIZATION = "Authorization"
            const val HEADER_CONTENT_TYPE = "Content-Type"
            const val HEADER_CONTENT_TYPE_VALUE = "application/json"
            const val HEADER_AUTHORIZATION_TYPE = "Bearer "
        }
    }

}