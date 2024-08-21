package org.interview.login.usecase

import kotlinx.coroutines.flow.Flow
import org.interview.login.models.SendAuthData
import org.interview.login.models.SendAuthResult
import org.interview.remote.models.Response

interface SendAuthUseCase {

    suspend operator fun invoke(request: SendAuthData): Flow<Response<out SendAuthResult>>

}