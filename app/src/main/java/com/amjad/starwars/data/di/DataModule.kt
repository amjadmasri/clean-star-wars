package com.amjad.starwars.data.di

import com.amjad.starwars.data.local.FilmLocalSource
import com.amjad.starwars.data.local.FilmLocalSourceImp
import com.amjad.starwars.data.remote.*
import com.amjad.starwars.data.repository.CharacterRepositoryImp
import com.amjad.starwars.data.repository.FilmRepositoryImp
import com.amjad.starwars.data.repository.PlanetRepositoryImp
import com.amjad.starwars.data.repository.SpeciesRepositoryImp
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.domain.repository.FilmRepository
import com.amjad.starwars.domain.repository.PlanetRepository
import com.amjad.starwars.domain.repository.SpeciesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun provideCharacterRemoteSource(characterRemoteSource: CharacterRemoteSourceImp): CharacterRemoteSource

    @Binds
    abstract fun provideCharacterRepository(characterRepositoryImp: CharacterRepositoryImp): CharacterRepository

    @Binds
    abstract fun providePlanetRemoteSource(planetRemoteSource: PlanetRemoteSourceImp): PlanetRemoteSource

    @Binds
    abstract fun providePlanetRepository(planetRepositoryImp: PlanetRepositoryImp): PlanetRepository

    @Binds
    abstract fun provideSpeciesRemoteSource(speciesRemoteSourceImp: SpeciesRemoteSourceImp): SpeciesRemoteSource

    @Binds
    abstract fun provideSpeciesRepository(speciesRepositoryImp: SpeciesRepositoryImp): SpeciesRepository

    @Binds
    abstract fun provideFilmRemoteSource(planetRemoteSource: FilmRemoteSourceImp): FilmRemoteSource

    @Binds
    abstract fun provideFilmLocalSource(filmLocalSourceImp: FilmLocalSourceImp): FilmLocalSource

    @Binds
    abstract fun provideFilmRepository(filmRepositoryImp: FilmRepositoryImp): FilmRepository

}