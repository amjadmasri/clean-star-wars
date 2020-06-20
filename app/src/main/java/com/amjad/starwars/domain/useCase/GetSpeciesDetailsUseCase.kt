package com.amjad.starwars.domain.useCase

import com.amjad.starwars.domain.extensions.toResult
import com.amjad.starwars.domain.mappers.SpeciesPresentationMapper
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.repository.SpeciesRepository
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import io.reactivex.Observable
import io.reactivex.Observable.zip
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSpeciesDetailsUseCase @Inject constructor(
    private val speciesRepository: SpeciesRepository,
    private val getPlanetDetailsUseCase: GetPlanetDetailsUseCase,
    private val speciesPresentationMapper: SpeciesPresentationMapper
) {

    fun execute(id: String): Observable<Result<SpeciesPresentationModel>> {
        return speciesRepository.getSpeciesDetails(id)
            .concatMap {species->
                if (species.homeWorld.isNotEmpty()) {
                    zip(Observable.just(species), getPlanetDetailsUseCase.execute(species.homeWorld),
                        BiFunction { speciesDomainModel, planetDomainModel ->
                            val presentation =
                                speciesPresentationMapper.fromDomainToPresentation(speciesDomainModel)
                            when(planetDomainModel){
                                is Result.OnSuccess ->  presentation.homeworld = planetDomainModel.data
                            }
                            return@BiFunction presentation.toResult()
                        })
                } else {
                    Observable.just(speciesPresentationMapper.fromDomainToPresentation(species).toResult())
                }
            }
    }
}