package com.amjad.starwars.domain.repository

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.common.models.Resource

interface SpeciesRepository {

    fun getSpeciesDetails(id:String):LiveData<Resource<SpeciesDomainModel>>
}