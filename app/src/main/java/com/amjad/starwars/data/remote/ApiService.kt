package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getCharacterDetails(@Url url :String):Single<CharacterDataModel>

    @GET
    fun getSpeciesDetails(@Url url :String):Single<SpeciesDataModel>

    @GET
    fun getFilmDetails(@Url url :String):Single<FilmRemoteDataModel>

    @GET
    fun getPlanetDetails(@Url url :String):Single<PlanetDataModel>

    @GET("people/")
    fun searchCharacterByName(@Query("search")name:String):Single<CharacterSearchResponse>

    @GET
    fun getMoreCharacters(@Url url :String):Single<CharacterSearchResponse>
}