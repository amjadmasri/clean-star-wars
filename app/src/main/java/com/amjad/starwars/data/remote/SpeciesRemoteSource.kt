package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.SpeciesDataModel
import io.reactivex.Single
import retrofit2.Response

interface SpeciesRemoteSource {

    fun getSpeciesDetails(id: String): Single<Response<SpeciesDataModel>>
}