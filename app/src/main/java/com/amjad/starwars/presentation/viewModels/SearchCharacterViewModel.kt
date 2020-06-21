package com.amjad.starwars.presentation.viewModels

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amjad.starwars.common.extensions.plusAssign
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.useCase.SearchCharacterUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchCharacterViewModel @Inject constructor(private val searchCharacterUseCase: SearchCharacterUseCase) :
    BaseViewModel() {

    private val currentSearchString: MutableLiveData<String> = MutableLiveData()
    private val characterList :MediatorLiveData<PagedList<CharacterDomainModel>> =
    MediatorLiveData()

    private val errorMessage: MutableLiveData<String> =MutableLiveData()

    init {
        currentSearchString.value = ""
    }

    fun observeErrorMessage():LiveData<String> = errorMessage

    fun observeSearchResults(): LiveData<PagedList<CharacterDomainModel>> {
       characterList.removeSource(currentSearchString)

        characterList.addSource(currentSearchString){ name->
            disposable+=searchCharacterUseCase.execute(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    when(it){
                        is Result.OnSuccess->{
                            characterList.postValue(it.data)
                        }
                        is Result.OnError -> errorMessage.value=it.starWarsError.message
                    }
                }
        }
        return characterList
    }



    fun setSearchString(name: String) {
        currentSearchString.value = name
    }

}