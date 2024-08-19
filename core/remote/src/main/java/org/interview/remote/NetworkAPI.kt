package org.interview.remote

import org.interview.remote.models.Response
import org.interview.remote.models.request.CheckAuthRequest
import org.interview.remote.models.request.RegistrationRequest
import org.interview.remote.models.request.SendAuthRequest
import org.interview.remote.models.request.UpdateProfileRequest
import org.interview.remote.models.response.CheckAuthResponse
import org.interview.remote.models.response.GetProfileResponse
import org.interview.remote.models.response.RegistrationResponse
import org.interview.remote.models.response.SendAuthResponse
import org.interview.remote.models.response.UpdateProfileResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface NetworkAPI {

    //---------------------------Auth-----------------------------
    @POST("/api/v1/users/send-auth-code/")
    fun sendAuthCode(request: SendAuthRequest): Response<SendAuthResponse>

    @POST("/api/v1/users/check-auth-code/")
    fun checkAuthCode(request: CheckAuthRequest): Response<CheckAuthResponse>

    @POST("/api/v1/users/register/")
    fun registration(request: RegistrationRequest): Response<RegistrationResponse>

    //--------------------------Profile---------------------------
    @GET("/api/v1/users/me/")
    fun getProfileData(): Response<GetProfileResponse>

    @PUT("/api/v1/users/me/")
    fun updateProfileData(request: UpdateProfileRequest): Response<UpdateProfileResponse>

}