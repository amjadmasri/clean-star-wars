package com.amjad.starwars.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amjad.starwars.data.models.FilmLocalDataModel
import io.reactivex.Completable

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(filmLocalDataModel: List<FilmLocalDataModel>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(flmLocalDataModel: FilmLocalDataModel): Completable

    @Query("SELECT * from films where  url=:url")
    fun getFilmByUrl(url:String): LiveData<FilmLocalDataModel>

}