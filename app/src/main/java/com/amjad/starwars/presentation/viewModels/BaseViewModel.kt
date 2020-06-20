package com.amjad.starwars.presentation.viewModels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    val disposable:CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}