package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.repository.PlanetRepository
import com.amjad.starwars.common.models.Resource
import javax.inject.Inject

class GetPlanetDetailsUseCase @Inject constructor(private val planetRepository: PlanetRepository) {

    fun execute(id:String):LiveData<Resource<PlanetDomainModel>>{
        return planetRepository.getPlanetDetails(id)
    }
}