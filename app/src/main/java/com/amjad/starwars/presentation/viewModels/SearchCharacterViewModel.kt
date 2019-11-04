package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.domain.useCase.SearchCharacterUseCase
import com.amjad.starwars.common.Resource
import javax.inject.Inject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.useCase.GetCharacterDetailsUseCase
import com.amjad.starwars.presentation.models.CharacterPresentationModel


class SearchCharacterViewModel @Inject constructor(private val searchCharacterUseCase: SearchCharacterUseCase) : ViewModel() {
    var networkState:LiveData<Resource<String>> = MutableLiveData<Resource<String>>()
    fun searchCharacterByName(name:String):LiveData<PagedList<CharacterDomainModel>>{
        val listing =searchCharacterUseCase.execute(name)
        networkState=listing.networkState

        return listing.pagedList
    }


}