package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.FilmRemoteDataModel
import io.reactivex.Single
import retrofit2.Response

interface FilmRemoteSource {

    fun getFilmDetails(id: String): Single<FilmRemoteDataModel>
}