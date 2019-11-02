package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.FilmRemoteDataModel
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class FilmRemoteSourceImp @Inject constructor(private val apiService: ApiService) :
    FilmRemoteSource {
    override fun getFilmDetails(id: String): Single<Response<FilmRemoteDataModel>> {
        return apiService.getfilmDetails(id)
    }
}