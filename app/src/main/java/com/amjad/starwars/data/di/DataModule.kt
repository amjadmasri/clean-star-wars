package com.amjad.starwars.data.di

import com.amjad.starwars.common.utilities.UrlExtractor
import com.amjad.starwars.data.local.FilmLocalSource
import com.amjad.starwars.data.local.FilmLocalSourceImp
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.mappers.FilmMapper
import com.amjad.starwars.data.mappers.PlanetMapper
import com.amjad.starwars.data.mappers.SpeciesMapper
import com.amjad.starwars.data.remote.*
import com.amjad.starwars.data.repository.CharacterRepositoryImp
import com.amjad.starwars.data.repository.FilmRepositoryImp
import com.amjad.starwars.data.repository.PlanetRepositoryImp
import com.amjad.starwars.data.repository.SpeciesRepositoryImp
import com.amjad.starwars.domain.repository.CharacterRepository
import com.amjad.starwars.domain.repository.FilmRepository
import com.amjad.starwars.domain.repository.PlanetRepository
import com.amjad.starwars.domain.repository.SpeciesRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCharacterRemoteSource(characterRemoteSource: CharacterRemoteSourceImp): CharacterRemoteSource =
        characterRemoteSource

    @Provides
    fun provideCharacterRepository(characterRepositoryImp: CharacterRepositoryImp): CharacterRepository =
        characterRepositoryImp

    @Provides
    fun provideCharacterMapper(urlExtractor: UrlExtractor): CharacterMapper = CharacterMapper(urlExtractor)


    @Provides
    fun providePlanetRemoteSource(planetRemoteSource: PlanetRemoteSourceImp): PlanetRemoteSource =
        planetRemoteSource

    @Provides
    fun providePlanetRepository(planetRepositoryImp: PlanetRepositoryImp): PlanetRepository =
        planetRepositoryImp

    @Provides
    fun providePlanetMapper(urlExtractor: UrlExtractor): PlanetMapper = PlanetMapper(urlExtractor)


    @Provides
    fun provideSpeciesRemoteSource(speciesRemoteSourceImp: SpeciesRemoteSourceImp): SpeciesRemoteSource =
        speciesRemoteSourceImp

    @Provides
    fun provideSpeciesRepository(speciesRepositoryImp: SpeciesRepositoryImp): SpeciesRepository =
        speciesRepositoryImp

    @Provides
    fun provideSpeciesMapper(urlExtractor: UrlExtractor): SpeciesMapper = SpeciesMapper(urlExtractor)

    @Provides
    fun provideFilmRemoteSource(planetRemoteSource: FilmRemoteSourceImp): FilmRemoteSource =
        planetRemoteSource

    @Provides
    fun provideFilmLocalSource(filmLocalSourceImp: FilmLocalSourceImp): FilmLocalSource =
        filmLocalSourceImp

    @Provides
    fun provideFilmRepository(filmRepositoryImp: FilmRepositoryImp): FilmRepository =
        filmRepositoryImp

    @Provides
    fun provideFilmMapper(urlExtractor: UrlExtractor): FilmMapper = FilmMapper(urlExtractor)
}