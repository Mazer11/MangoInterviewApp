package org.interview.local.database.repository

import kotlinx.coroutines.flow.Flow
import org.interview.local.database.LocalDatabase
import org.interview.local.database.model.ProfileEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImpl @Inject constructor(
    private val db: LocalDatabase
) : LocalRepository {
    override suspend fun selectProfile(): Flow<ProfileEntity> = db.dbDao.selectProfile()

    override fun updateProfile(data: ProfileEntity) = db.dbDao.updateProfile(data)
}