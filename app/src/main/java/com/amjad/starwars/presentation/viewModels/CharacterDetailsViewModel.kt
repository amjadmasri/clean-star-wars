package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amjad.starwars.common.extensions.plusAssign
import com.amjad.starwars.domain.models.Result
import com.amjad.starwars.domain.useCase.GetCharacterDetailsUseCase
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase) :
    BaseViewModel() {
    private val result = MutableLiveData<CharacterPresentationModel>()

    fun observeCharacterResult() :LiveData<CharacterPresentationModel> = result
    fun getCharacterDetails(id: String) {

        disposable += getCharacterDetailsUseCase.execute(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is Result.OnSuccess -> result.value = it.data
                }
            }
    }
}