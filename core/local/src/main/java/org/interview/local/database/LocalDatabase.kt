package org.interview.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.interview.local.database.dao.LocalDataDAO
import org.interview.local.database.model.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract val dbDao: LocalDataDAO
}