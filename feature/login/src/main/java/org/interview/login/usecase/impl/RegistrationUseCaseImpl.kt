package org.interview.login.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.interview.login.models.RegistrationData
import org.interview.login.models.RegistrationResult
import org.interview.login.models.toRequest
import org.interview.login.models.toResult
import org.interview.login.usecase.RegistrationUseCase
import org.interview.remote.models.ErrorContent
import org.interview.remote.models.Response
import org.interview.remote.repository.auth.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegistrationUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : RegistrationUseCase {

    override suspend fun invoke(request: RegistrationData): Flow<Response<out RegistrationResult>> =
        flow {
            val response = repository.registration(request.toRequest())

            if (response is Response.Success && response.result != null) {
                emit(Response.Success(response.result!!.toResult()))
            } else {
                emit(Response.Error((response as Response.Error).message))
            }
        }
}