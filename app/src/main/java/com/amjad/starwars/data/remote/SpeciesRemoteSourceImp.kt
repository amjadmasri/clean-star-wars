package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.SpeciesDataModel
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class SpeciesRemoteSourceImp @Inject constructor(private val apiService: ApiService) :SpeciesRemoteSource{
    override fun getSpeciesDetails(id: String): Single<SpeciesDataModel> {
        return apiService.getSpeciesDetails(id)
    }
}