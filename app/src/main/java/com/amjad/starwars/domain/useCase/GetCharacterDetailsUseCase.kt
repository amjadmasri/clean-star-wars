package com.amjad.starwars.domain.useCase

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amjad.starwars.common.Resource
import com.amjad.starwars.common.Status
import com.amjad.starwars.domain.mappers.CharacterPresentationMapper
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getPlanetDetailsUseCase: GetPlanetDetailsUseCase,
    private val getSpeciesDetailsUseCase: GetSpeciesDetailsUseCase,
    private val getFilmsListUseCase: GetFilmsListUseCase,
    private val characterPresentationMapper: CharacterPresentationMapper
) {

    @SuppressLint("LogNotTimber")
    fun execute(id: String): LiveData<Resource<CharacterPresentationModel>> {
        var characterDetails: CharacterPresentationModel
        val mediatorLiveData: MediatorLiveData<Resource<CharacterPresentationModel>> =
            MediatorLiveData()
        val characterSource = characterRepository.getCharacterDetails(id)
        mediatorLiveData.addSource(characterSource) {
            Log.d("saed", "status is " + it.status)
            if (it.status == Status.SUCCESS) {
                Log.d("saed", "in success")
                characterDetails = characterPresentationMapper.fromDomainToPresentation(it.data)
                mediatorLiveData.removeSource(characterSource)
                val planetSource = getPlanetDetailsUseCase.execute(it.data!!.homeworld)
                mediatorLiveData.addSource(planetSource) { planet ->
                    if (planet.status == Status.SUCCESS) {
                        characterDetails.homeworld = planet.data!!
                        mediatorLiveData.postValue(Resource.success(characterDetails))

                    }
                }

                val speciesSource = getSpeciesDetailsUseCase.execute(it.data.species[0])
                mediatorLiveData.addSource(speciesSource) { species ->
                    if (species.status == Status.SUCCESS) {
                        characterDetails.species = species.data
                        mediatorLiveData.postValue(Resource.success(characterDetails))

                    }
                }

                val filmSource = getFilmsListUseCase.execute(it.data.films)
                mediatorLiveData.addSource(filmSource) { films ->
                    if (films.status == Status.SUCCESS) {
                        Log.d("wisam", " inside fild " + (films.data?.url ?: "hoho"))
                        characterDetails.films.add(films.data!!)

                        mediatorLiveData.postValue(Resource.success(characterDetails))
                    }
                }
            }
        }
        return mediatorLiveData
    }


}