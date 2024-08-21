package org.interview.login.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.interview.login.usecase.CheckAuthUseCase
import org.interview.login.usecase.RegistrationUseCase
import org.interview.login.usecase.SendAuthUseCase
import org.interview.login.usecase.impl.CheckAuthUseCaseImpl
import org.interview.login.usecase.impl.RegistrationUseCaseImpl
import org.interview.login.usecase.impl.SendAuthUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCaseBindModule {

    @Binds
    fun bindsSendAuthUseCase(impl: SendAuthUseCaseImpl): SendAuthUseCase

    @Binds
    fun bindsCheckAuthUseCase(impl: CheckAuthUseCaseImpl): CheckAuthUseCase

    @Binds
    fun bindsRegistrationUseCase(impl: RegistrationUseCaseImpl): RegistrationUseCase



}