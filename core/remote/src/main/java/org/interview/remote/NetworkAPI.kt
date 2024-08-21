package org.interview.remote

import org.interview.remote.models.request.CheckAuthRequest
import org.interview.remote.models.request.RegistrationRequest
import org.interview.remote.models.request.SendAuthRequest
import org.interview.remote.models.request.UpdateProfileRequest
import org.interview.remote.models.response.CheckAuthResponse
import org.interview.remote.models.response.GetProfileResponse
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.models.response.SendAuthResponse
import org.interview.remote.models.response.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface NetworkAPI {

    //---------------------------Auth-----------------------------
    @POST("/api/v1/users/send-auth-code/")
    @Headers("Content-Type: application/json")
    suspend fun sendAuthCode(@Body request: SendAuthRequest): SendAuthResponse

    @POST("/api/v1/users/check-auth-code/")
    @Headers("Content-Type: application/json")
    suspend fun checkAuthCode(@Body request: CheckAuthRequest): CheckAuthResponse

    @POST("/api/v1/users/register/")
    @Headers("Content-Type: application/json")
    suspend fun registration(@Body request: RegistrationRequest): RegistrationResponse

    //--------------------------Profile---------------------------
    @GET("/api/v1/users/me/")
    suspend fun getProfileData(): GetProfileResponse

    @PUT("/api/v1/users/me/")
    @Headers("Content-Type: application/json")
    suspend fun updateProfileData(@Body request: UpdateProfileRequest): UpdateProfileResponse

}