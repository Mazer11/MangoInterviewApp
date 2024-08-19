package org.interview.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.interview.local.database.model.ProfileEntity

@Dao
interface LocalDataDAO {

    @Query("SELECT * FROM profile")
    fun selectProfile(): Flow<ProfileEntity>

    @Update
    fun updateProfile(data: ProfileEntity)

}