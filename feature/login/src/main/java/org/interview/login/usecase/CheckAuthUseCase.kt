package org.interview.login.usecase

import kotlinx.coroutines.flow.Flow
import org.interview.login.models.CheckAuthData
import org.interview.login.models.CheckAuthResult
import org.interview.remote.models.Response

interface CheckAuthUseCase {

    suspend operator fun invoke(request: CheckAuthData): Flow<Response<out CheckAuthResult>>

}