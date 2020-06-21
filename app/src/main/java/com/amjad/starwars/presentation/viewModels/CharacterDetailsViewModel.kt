package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.common.extensions.plusAssign
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.useCase.GetCharacterDetailsUseCase
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase) :
    BaseViewModel() {
    private val result = MutableLiveData<CharacterPresentationModel>()
    private val speciesDetails = MutableLiveData<SpeciesPresentationModel>()
    private val filmList = MutableLiveData<List<FilmDomainModel>>()

    private val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun observeCharacterResult() :LiveData<CharacterPresentationModel> = result
    fun observeSpeciesDetails() :LiveData<SpeciesPresentationModel> = speciesDetails
    fun observeFilmList() :LiveData<List<FilmDomainModel>> = filmList

    fun observeErrorMessage():LiveData<String> = errorMessage
    fun getCharacterDetails(id: String) {

        disposable += getCharacterDetailsUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Result.OnSuccess -> {
                        result.value = it.data

                        it.data.species?.let { species->
                            speciesDetails.value=species
                        }

                        if(it.data.films.isNotEmpty())filmList.value=it.data.films
                    }
                    is Result.OnError -> errorMessage.value=it.starWarsError.message
                }
            }
    }
}