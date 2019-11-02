package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.domain.useCase.SearchCharacterUseCase
import com.amjad.starwars.presentation.Resource
import javax.inject.Inject
import androidx.lifecycle.MediatorLiveData
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.lifecycle.Observer


class SearchCharacterViewModel @Inject constructor(private val searchCharacterUseCase: SearchCharacterUseCase) : ViewModel() {
    private var characterList:MutableLiveData<Resource<PagedList<CharacterDataModel>>> = MutableLiveData<Resource<PagedList<CharacterDataModel>>>()
    fun searchCharacterByName(name:String):LiveData<Resource<PagedList<CharacterDataModel>>>{
        val liveDataMerger = MediatorLiveData<Resource<PagedList<CharacterDataModel>>>()
        liveDataMerger.postValue(Resource.loading(null))
        liveDataMerger.addSource(searchCharacterUseCase.execute(name), Observer {
            liveDataMerger.postValue(Resource.success(it))
        })
        return liveDataMerger
    }
}