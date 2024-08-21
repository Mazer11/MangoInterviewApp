package org.interview.profile.usecase

import kotlinx.coroutines.flow.Flow
import org.interview.profile.models.UpdateProfileData
import org.interview.profile.models.UpdateProfileResult
import org.interview.remote.models.Response

interface UpdateProfileUseCase {

    suspend operator fun invoke(request: UpdateProfileData): Flow<Response<out UpdateProfileResult>>

}