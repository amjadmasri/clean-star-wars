package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.useCase.SearchCharacterUseCase
import javax.inject.Inject


class SearchCharacterViewModel @Inject constructor(private val searchCharacterUseCase: SearchCharacterUseCase) :
    ViewModel() {
    private var networkState: MediatorLiveData<Resource<String>> = MediatorLiveData<Resource<String>>()
    private val currentSearchString: MutableLiveData<String> = MutableLiveData()

    private val items: LiveData<PagedList<CharacterDomainModel>> =
        Transformations.switchMap(currentSearchString) { s ->
            val listing = searchCharacterUseCase.execute(s)
            networkState.addSource(listing.networkState) {
                networkState.postValue(it)
            }
            listing.pagedList

        }

    init {
        currentSearchString.value = ""
    }

    fun observeSearchResults(): LiveData<PagedList<CharacterDomainModel>> {
        return items
    }

    fun observeNetworkState():LiveData<Resource<String>>{
        return networkState
    }

    fun setStringListener(name: String) {
        currentSearchString.value = name
    }

}