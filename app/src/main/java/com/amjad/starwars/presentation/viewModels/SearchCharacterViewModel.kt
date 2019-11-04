package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amjad.starwars.domain.useCase.SearchCharacterUseCase
import com.amjad.starwars.common.Resource
import javax.inject.Inject
import com.amjad.starwars.domain.models.CharacterDomainModel


class SearchCharacterViewModel @Inject constructor(private val searchCharacterUseCase: SearchCharacterUseCase) : ViewModel() {
    var networkState:LiveData<Resource<String>> = MutableLiveData<Resource<String>>()
    private val currentSearchString: MutableLiveData<String> = MutableLiveData()

    private val items: LiveData<PagedList<CharacterDomainModel>> = Transformations.switchMap(currentSearchString) { s ->
        val listing =searchCharacterUseCase.execute(s)
        networkState=listing.networkState
        listing.pagedList

    }

    init {
        currentSearchString.value = ""
    }

    fun observeSearchResults():LiveData<PagedList<CharacterDomainModel>>{
        return items
    }

    fun setStringListener(name:String){
        currentSearchString.value=name
    }

}