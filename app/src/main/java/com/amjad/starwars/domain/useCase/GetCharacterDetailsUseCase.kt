package com.amjad.starwars.domain.useCase


import com.amjad.starwars.domain.extensions.toResult
import com.amjad.starwars.domain.mappers.CharacterPresentationMapper
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.models.Result
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

    fun execute(id: String): Observable<Result<CharacterPresentationModel>> {

        return characterRepository.getCharacterDetails(id)
            .concatMap { character ->
                zip(
                    Observable.just(character),
                    getSpeciesDetailsUseCase.execute(character.species.getOrNull(0)?:""),
                    getFilmsListUseCase.execute(character.films),
                    Function3<CharacterDomainModel, Result<SpeciesPresentationModel>, Result<List<FilmDomainModel>>, Result<CharacterPresentationModel>> {
                            characterDomainModel, speciesPresentationModel, filmList ->
                        val presentation = characterPresentationMapper.fromDomainToPresentation(characterDomainModel)
                        when (speciesPresentationModel){
                            is Result.OnSuccess-> presentation.species = speciesPresentationModel.data
                        }
                        when(filmList){
                            is Result.OnSuccess ->presentation.films = filmList.data
                        }

                        return@Function3 presentation.toResult()
                    }
                )

            }

    }

}