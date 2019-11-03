package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.domain.repository.SpeciesRepository
import com.amjad.starwars.common.Resource
import javax.inject.Inject

class GetSpeciesDetailsUseCase @Inject constructor(private val speciesRepository: SpeciesRepository) {

    fun getSpeciesDetails(id:String):LiveData<Resource<SpeciesDomainModel>>{
        return speciesRepository.getSpeciesDetails(id)
    }
}