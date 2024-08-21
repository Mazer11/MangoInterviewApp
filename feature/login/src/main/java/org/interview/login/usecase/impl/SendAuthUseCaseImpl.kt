package org.interview.login.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.interview.login.models.SendAuthData
import org.interview.login.models.SendAuthResult
import org.interview.login.models.toRequest
import org.interview.login.models.toResult
import org.interview.login.usecase.SendAuthUseCase
import org.interview.remote.models.Response
import org.interview.remote.repository.auth.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendAuthUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : SendAuthUseCase {

    override suspend fun invoke(request: SendAuthData): Flow<Response<out SendAuthResult>> = flow {
        val response = repository.sendAuthCode(request.toRequest())

        if (response is Response.Success && response.result != null) {
            emit(Response.Success(response.result!!.toResult()))
        } else {
            emit(Response.Error((response as Response.Error).message))
        }
    }
}