package com.amjad.starwars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amjad.starwars.data.local.dao.FilmDao
import com.amjad.starwars.data.models.FilmLocalDataModel

@Database(entities = [FilmLocalDataModel::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}