package com.amjad.starwars.common.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amjad.starwars.common.di.interfaces.ViewModelKey
import com.amjad.starwars.presentation.viewModels.CharacterDetailsViewModel
import com.amjad.starwars.presentation.viewModels.SearchCharacterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.amjad.starwars.presentation.viewModels.ViewModelProviderFactory


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(SearchCharacterViewModel::class)
    abstract fun bindSearchCharacterViewModel(searchCharacterViewModel: SearchCharacterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun bindCharacterDetailsViewModel(characterDetailsViewModel: CharacterDetailsViewModel): ViewModel


}