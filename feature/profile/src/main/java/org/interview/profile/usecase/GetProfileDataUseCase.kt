package org.interview.profile.usecase

import kotlinx.coroutines.flow.Flow
import org.interview.profile.models.GetProfileResult
import org.interview.remote.models.Response

interface GetProfileDataUseCase {

    suspend operator fun invoke(): Flow<Response<out GetProfileResult>>

}