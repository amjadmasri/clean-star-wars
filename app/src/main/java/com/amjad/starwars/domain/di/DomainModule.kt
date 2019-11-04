package com.amjad.starwars.domain.di

import com.amjad.starwars.domain.mappers.CharacterPresentationMapper
import com.amjad.starwars.domain.mappers.SpeciesPresentationMapper
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideCharacterPresentationMapper():CharacterPresentationMapper =CharacterPresentationMapper()

    @Provides
    fun provideSpeciesPresentationMapper():SpeciesPresentationMapper = SpeciesPresentationMapper()
}