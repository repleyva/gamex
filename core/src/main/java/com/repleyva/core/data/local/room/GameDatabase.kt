package com.repleyva.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.repleyva.core.data.local.dao.GameDao
import com.repleyva.core.data.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringTypeConverter::class, DateTypeConverter::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}