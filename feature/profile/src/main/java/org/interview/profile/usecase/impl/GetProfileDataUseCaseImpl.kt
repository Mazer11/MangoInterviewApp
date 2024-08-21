package org.interview.profile.usecase.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.interview.profile.models.GetProfileResult
import org.interview.profile.models.toProfile
import org.interview.profile.models.toResult
import org.interview.profile.usecase.GetProfileDataUseCase
import org.interview.remote.models.Response
import org.interview.remote.repository.profile.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProfileDataUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetProfileDataUseCase {

    override suspend fun invoke(): Flow<Response<out GetProfileResult>> = flow {
        val response = repository.getProfileData()

        if (response is Response.Success && response.result != null) {
            emit(Response.Success(response.result!!.toResult()))
        } else {
            emit(Response.Error((response as Response.Error).message))
        }
    }

}