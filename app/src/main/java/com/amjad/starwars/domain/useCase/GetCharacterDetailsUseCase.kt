package com.amjad.starwars.domain.useCase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.common.models.Status
import com.amjad.starwars.domain.mappers.CharacterPresentationMapper
import com.amjad.starwars.domain.mappers.SpeciesPresentationMapper
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getPlanetDetailsUseCase: GetPlanetDetailsUseCase,
    private val getSpeciesDetailsUseCase: GetSpeciesDetailsUseCase,
    private val getFilmsListUseCase: GetFilmsListUseCase,
    private val characterPresentationMapper: CharacterPresentationMapper,
    private val speciesPresentationMapper: SpeciesPresentationMapper
) {
    private lateinit var characterDetails: CharacterPresentationModel
    private val mediatorLiveData: MediatorLiveData<Resource<CharacterPresentationModel>> =
        MediatorLiveData()

    fun execute(id: String): LiveData<Resource<CharacterPresentationModel>> {

        val characterSource = characterRepository.getCharacterDetails(id)

        mediatorLiveData.addSource(characterSource) {
            if (it.status == Status.SUCCESS) {
                characterDetails = characterPresentationMapper.fromDomainToPresentation(it.data)
                mediatorLiveData.removeSource(characterSource)

                getSpeciesDetails(it.data!!.species[0])

                getFilmsDetails(it.data.films)
            }
            else if (it.status== Status.ERROR){
                mediatorLiveData.postValue(Resource.error("couldn't load character details"))
            }
        }
        return mediatorLiveData
    }

    private fun getSpeciesDetails(id:String){
        val speciesSource = getSpeciesDetailsUseCase.execute(id)
        mediatorLiveData.addSource(speciesSource) { species ->
            if (species.status == Status.SUCCESS) {
                val speciesPresentation =
                    speciesPresentationMapper.fromDomainToPresentation(species.data!!)
                characterDetails.species = speciesPresentation
                mediatorLiveData.postValue(Resource.success(characterDetails))
                if (species.data.homeworld.isNotEmpty()) {
                  getSpeciesHomeworldDetails(species.data.homeworld)
                }
            }
            else if (species.status== Status.ERROR){
                mediatorLiveData.postValue(Resource.error("couldn't load species details"))
            }
        }
    }

    private fun getFilmsDetails(ids: List<String>){
        val filmSource = getFilmsListUseCase.execute(ids)
        mediatorLiveData.addSource(filmSource) { films ->
            if (films.status == Status.SUCCESS) {
                if (!characterDetails.films.contains(films.data!!))
                    characterDetails.films.add(films.data!!)

                mediatorLiveData.postValue(Resource.success(characterDetails))
            }
            else if (films.status== Status.ERROR){
                mediatorLiveData.postValue(Resource.error("couldn't load films details"))
            }
        }
    }

    private fun getSpeciesHomeworldDetails(id:String){
        val planetSource =
            getPlanetDetailsUseCase.execute(id)
        mediatorLiveData.addSource(planetSource) { planet ->
            if (planet.status == Status.SUCCESS) {

                characterDetails.species!!.homeworld = planet.data!!
                mediatorLiveData.postValue(Resource.success(characterDetails))

            } else if (planet.status == Status.ERROR) {
                mediatorLiveData.postValue(Resource.error("couldn't load home world details"))
            }
        }
    }
}