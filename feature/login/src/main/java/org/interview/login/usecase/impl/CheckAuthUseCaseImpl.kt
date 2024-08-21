package org.interview.login.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.interview.login.models.CheckAuthData
import org.interview.login.models.CheckAuthResult
import org.interview.login.models.toRequest
import org.interview.login.models.toResult
import org.interview.login.usecase.CheckAuthUseCase
import org.interview.remote.models.ErrorContent
import org.interview.remote.models.Response
import org.interview.remote.repository.auth.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckAuthUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : CheckAuthUseCase {

    override suspend operator fun invoke(request: CheckAuthData): Flow<Response<out CheckAuthResult>> =
        flow {
            val response = repository.checkAuthCode(request.toRequest())

            if (response is Response.Success && response.result != null) {
                emit(Response.Success(response.result!!.toResult()))
            } else {
                emit(Response.Error((response as Response.Error).message))
            }
        }
}