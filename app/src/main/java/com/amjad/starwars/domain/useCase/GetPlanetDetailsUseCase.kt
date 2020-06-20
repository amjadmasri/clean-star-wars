package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.repository.PlanetRepository
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.domain.extensions.toResult
import com.amjad.starwars.domain.models.Result
import io.reactivex.Observable
import javax.inject.Inject

class GetPlanetDetailsUseCase @Inject constructor(private val planetRepository: PlanetRepository) {

    fun execute(id:String):Observable<Result<PlanetDomainModel>>{
        return planetRepository.getPlanetDetails(id)
            .map { it.toResult() }
            .onErrorReturn { it.toResult() }
    }
}