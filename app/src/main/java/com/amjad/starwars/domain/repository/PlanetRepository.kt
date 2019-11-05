package com.amjad.starwars.domain.repository

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.common.models.Resource

interface PlanetRepository {

    fun getPlanetDetails(id:String):LiveData<Resource<PlanetDomainModel>>
}