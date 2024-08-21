package org.interview.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.interview.profile.usecase.GetProfileDataUseCase
import org.interview.profile.usecase.UpdateProfileUseCase
import org.interview.profile.usecase.impl.GetProfileDataUseCaseImpl
import org.interview.profile.usecase.impl.UpdateProfileUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface ProfileUseCaseBindModule {

    @Binds
    fun bindUpdateProfileUseCase(impl: UpdateProfileUseCaseImpl): UpdateProfileUseCase

    @Binds
    fun bindGetProfileDataUseCase(impl: GetProfileDataUseCaseImpl): GetProfileDataUseCase

}