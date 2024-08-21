package org.interview.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.interview.remote.repository.auth.AuthRepository
import org.interview.remote.repository.auth.AuthRepositoryImpl
import org.interview.remote.repository.profile.ProfileRepository
import org.interview.remote.repository.profile.ProfileRepositoryImpl
import org.interview.remote.repository.settings.SettingRepository
import org.interview.remote.repository.settings.SettingRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface RemoteRepositoryModule {

    @Binds
    fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindsProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindsSettingRepository(impl: SettingRepositoryImpl): SettingRepository

}