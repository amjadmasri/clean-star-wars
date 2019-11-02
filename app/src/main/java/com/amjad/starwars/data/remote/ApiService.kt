package com.amjad.starwars.data.remote

import com.amjad.starwars.data.models.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("people/{character_id}")
    fun getCharacterDetails(@Path("character_id")characterId:String):Single<Response<CharacterDataModel>>

    @GET("species/{species_id}")
    fun getSpeciesDetails(@Path("species_id")speciesId:String):Single<Response<SpeciesDataModel>>

    @GET("films/{film_id}")
    fun getfilmDetails(@Path("film_id")filmId:String):Single<Response<FilmRemoteDataModel>>

    @GET("planets/{planet_id}")
    fun getplanetDetails(@Path("planet_id")planetId:String):Single<Response<PlanetDataModel>>

    @GET("people/")
    fun searchCharacterByName(@Query("search")name:String,@Query("page")page:String?):Single<Response<CharacterSearchResponse>>
}