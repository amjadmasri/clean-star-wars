package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.SpeciesDomainModel
import com.amjad.starwars.domain.repository.SpeciesRepository
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.domain.mappers.SpeciesPresentationMapper
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import io.reactivex.Observable
import io.reactivex.Observable.zip
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetSpeciesDetailsUseCase @Inject constructor(private val speciesRepository: SpeciesRepository,private val getPlanetDetailsUseCase: GetPlanetDetailsUseCase,private val speciesPresentationMapper: SpeciesPresentationMapper) {

    fun execute(id:String):Observable<SpeciesPresentationModel>{
        return speciesRepository.getSpeciesDetails(id)
            .concatMap {
                if(it.homeworld.isNotEmpty()){
                    zip<SpeciesDomainModel,PlanetDomainModel,SpeciesPresentationModel>(Observable.just(it),getPlanetDetailsUseCase.execute(it.homeworld),
                        BiFunction { t1, t2 ->
                            val presentation = speciesPresentationMapper.fromDomainToPresentation(t1)
                            presentation.homeworld=t2
                            return@BiFunction presentation
                        })
                }
                else{
                    Observable.just(speciesPresentationMapper.fromDomainToPresentation(it))
                }
            }
    }
}