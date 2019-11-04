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


    fun getCharacterDetails(id:String): LiveData<Resource<CharacterPresentationModel>> {
        val liveDataMerger = MediatorLiveData<Resource<CharacterPresentationModel>>()
        liveDataMerger.postValue(Resource.loading(null))

        liveDataMerger.addSource(getCharacterDetailsUseCase.execute(id)) {
            liveDataMerger.postValue(Resource.success(it.data))
        }
        return liveDataMerger
    }
}