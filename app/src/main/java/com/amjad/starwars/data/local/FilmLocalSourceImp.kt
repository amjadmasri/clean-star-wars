package com.amjad.starwars.data.local

import androidx.lifecycle.LiveData
import com.amjad.starwars.data.models.FilmLocalDataModel
import io.reactivex.*
import javax.inject.Inject

class FilmLocalSourceImp @Inject constructor(private val appDatabase: AppDatabase):FilmLocalSource {
    override fun insertFilm(filmLocalDataModel: FilmLocalDataModel): Completable {
        return appDatabase.filmDao().insert(filmLocalDataModel)
    }

    override fun getFilmById(id: String): Single<FilmLocalDataModel> {
       return appDatabase.filmDao().getFilmByResourceId(id)
    }
}