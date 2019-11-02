package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.PlanetDataModel
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class PlanetRemoteSourceImp @Inject constructor(private val apiService: ApiService) :PlanetRemoteSource{
    override fun getPlanetDetails(id: String): Single<Response<PlanetDataModel>> {
        return apiService.getplanetDetails(id)
    }
}