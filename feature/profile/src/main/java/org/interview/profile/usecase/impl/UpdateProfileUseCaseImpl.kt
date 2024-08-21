package org.interview.profile.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.interview.profile.models.UpdateProfileData
import org.interview.profile.models.UpdateProfileResult
import org.interview.profile.models.toRequest
import org.interview.profile.models.toResult
import org.interview.profile.usecase.UpdateProfileUseCase
import org.interview.remote.models.Response
import org.interview.remote.repository.profile.ProfileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : UpdateProfileUseCase {

    override suspend fun invoke(request: UpdateProfileData): Flow<Response<out UpdateProfileResult>> =
        flow {
            val response = repository.updateProfileData(request.toRequest())

            if (response is Response.Success && response.result != null) {
                emit(Response.Success(response.result!!.toResult()))
            } else {
                emit(Response.Error((response as Response.Error).message))
            }
        }
}