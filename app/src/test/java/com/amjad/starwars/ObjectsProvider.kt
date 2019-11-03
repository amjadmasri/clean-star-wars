package com.amjad.starwars

import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.models.FilmRemoteDataModel

object ObjectsProvider{

    fun provideFilmLocalDataModel(id:String)
         = FilmLocalDataModel(1,"","","","","","","",id,"")

    fun provideFilmRemoteModel()= FilmRemoteDataModel(listOf("1"),"created","director","edited",1,
        "crawl", listOf("http://www.example.com/1","http://www.example.com/2"),"prod","releaseDate", listOf("http://www.example.com/3","http://www.example.com/4"), listOf("http://www.example.com/7"),"title","http://www.example.com/1",
        listOf())

    fun provideFilmLocalModel()= FilmLocalDataModel(1,"created","director","edited","1",
        "prod", "release","title","1", "http://www.example.com/1")
}