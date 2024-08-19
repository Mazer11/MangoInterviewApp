package org.interview.local.database.repository

import kotlinx.coroutines.flow.Flow
import org.interview.local.database.model.ProfileEntity

interface LocalRepository {

    suspend fun selectProfile(): Flow<ProfileEntity>

    fun updateProfile(data: ProfileEntity)

}