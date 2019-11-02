package com.amjad.starwars.data.local

import androidx.lifecycle.LiveData
import com.amjad.starwars.data.models.FilmLocalDataModel
import io.reactivex.Completable

interface FilmLocalSource {
    fun insertFilm(filmLocalDataModel: FilmLocalDataModel):Completable

    fun getFilmByURL(url:String):LiveData<FilmLocalDataModel>
}