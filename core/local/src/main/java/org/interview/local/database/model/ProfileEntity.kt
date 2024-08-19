package org.interview.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val status: String,
    val avatar: String,
    val phone: String
)
