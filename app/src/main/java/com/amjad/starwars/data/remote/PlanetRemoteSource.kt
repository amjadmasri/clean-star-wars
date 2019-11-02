package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.PlanetDataModel
import io.reactivex.Single

interface PlanetRemoteSource {

    fun getPlanetDetails(id: String): Single<retrofit2.Response<PlanetDataModel>>
}