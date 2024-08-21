package org.interview.login.usecase

import kotlinx.coroutines.flow.Flow
import org.interview.login.models.RegistrationData
import org.interview.login.models.RegistrationResult
import org.interview.remote.models.Response

interface RegistrationUseCase {

    suspend operator fun invoke(request: RegistrationData): Flow<Response<out RegistrationResult>>

}