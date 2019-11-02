package com.amjad.starwars.common.di.builders

import com.amjad.starwars.presentation.ui.di.CharacterAdapterModule
import com.amjad.starwars.presentation.ui.searchCharacters.SearchCharactersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [CharacterAdapterModule::class])
    abstract fun contributeSearchCharractersFragment(): SearchCharactersFragment

}