package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.FilmRemoteDataModel
import io.reactivex.Single
import javax.inject.Inject

class FilmRemoteSourceImp @Inject constructor(private val apiService: ApiService) :
    FilmRemoteSource {
    override fun getFilmDetails(id: String): Single<FilmRemoteDataModel> {
        return apiService.getFilmDetails(id)
    }
}