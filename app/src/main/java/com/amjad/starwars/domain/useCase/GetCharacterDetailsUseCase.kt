package com.amjad.starwars.domain.useCase


import com.amjad.starwars.domain.mappers.CharacterPresentationMapper
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import io.reactivex.Observable
import io.reactivex.Observable.zip
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getSpeciesDetailsUseCase: GetSpeciesDetailsUseCase,
    private val getFilmsListUseCase: GetFilmsListUseCase,
    private val characterPresentationMapper: CharacterPresentationMapper
) {

    fun execute(id: String): Observable<CharacterPresentationModel> {

        return characterRepository.getCharacterDetails(id)
            .concatMap {
                zip<CharacterDomainModel, SpeciesPresentationModel, List<FilmDomainModel>, CharacterPresentationModel>(
                    Observable.just(it),
                    getSpeciesDetailsUseCase.execute(it.species[0]),
                    getFilmsListUseCase.execute(it.films),
                    Function3<CharacterDomainModel, SpeciesPresentationModel, List<FilmDomainModel>, CharacterPresentationModel> { t1, t2, t3 ->
                        val presentation = characterPresentationMapper.fromDomainToPresentation(t1)
                        presentation.species = t2
                        presentation.films = t3.toMutableList()

                        return@Function3 presentation
                    }
                )
            }
    }

}