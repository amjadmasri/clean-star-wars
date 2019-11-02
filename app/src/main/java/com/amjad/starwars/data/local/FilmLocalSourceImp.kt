package com.amjad.starwars.data.local

import androidx.lifecycle.LiveData
import com.amjad.starwars.data.models.FilmLocalDataModel
import io.reactivex.Completable
import javax.inject.Inject

class FilmLocalSourceImp @Inject constructor(private val appDatabase: AppDatabase):FilmLocalSource {
    override fun insertFilm(filmLocalDataModel: FilmLocalDataModel): Completable {
        return appDatabase.filmDao().insert(filmLocalDataModel)
    }

    override fun getFilmByURL(url: String): LiveData<FilmLocalDataModel> {
       return appDatabase.filmDao().getFilmByUrl(url)
    }
}