package org.interview.profile.usecase

import kotlinx.coroutines.flow.Flow
import org.interview.profile.models.GetProfileResult
import org.interview.remote.models.Response
import org.interview.remote.models.response.GetProfileResponse

interface GetProfileDataUseCase {

    suspend operator fun invoke(): Flow<Response<out GetProfileResult>>

}