package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.amjad.starwars.common.Resource
import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.domain.useCase.GetCharacterDetailsUseCase
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,private val urlExtractor: UrlExtractor): ViewModel() {
    private val result = MediatorLiveData<Resource<CharacterPresentationModel>>()

    private fun retrieveCharacterDetails(id:String) {

        result.postValue(Resource.loading(null))

        result.addSource(getCharacterDetailsUseCase.execute(id)) {
            result.setValue(Resource.success(it.data))
        }

    }

    fun getCharacterDetails(id: String): LiveData<Resource<CharacterPresentationModel>>{

        retrieveCharacterDetails(id)

        return result
    }
}