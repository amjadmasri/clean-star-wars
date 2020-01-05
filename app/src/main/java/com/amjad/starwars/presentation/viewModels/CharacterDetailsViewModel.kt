package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.common.models.Status
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.domain.useCase.GetCharacterDetailsUseCase
import com.amjad.starwars.domain.useCase.GetPlanetDetailsUseCase
import com.amjad.starwars.domain.useCase.GetSpeciesDetailsUseCase
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import io.reactivex.Observable
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,private val getSpeciesDetailsUseCase: GetSpeciesDetailsUseCase) :
    ViewModel() {
    private val result = MediatorLiveData<Resource<CharacterPresentationModel>>()


    fun getCharacterDetails(id: String): Observable<CharacterPresentationModel> {

        return getCharacterDetailsUseCase.execute(id)
    }

    fun getPlanetDetails(id:String):Observable<SpeciesPresentationModel>{
        return getSpeciesDetailsUseCase.execute(id)
    }
}