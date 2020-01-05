package com.amjad.starwars.domain.repository

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.common.models.Resource
import io.reactivex.Observable

interface PlanetRepository {

    fun getPlanetDetails(id:String):Observable<PlanetDomainModel>
}