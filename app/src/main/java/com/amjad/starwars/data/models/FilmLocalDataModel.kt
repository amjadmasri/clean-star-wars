package com.amjad.starwars.data.models


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "films", indices = [Index(value = ["episodeId"], unique = true)])
data class FilmLocalDataModel(

    @PrimaryKey
    val episodeId: Int,
    val created: String,
    val director: String,
    val edited: String,
    val openingCrawl: String,
    val producer: String,
    val releaseDate: String,
    val title: String,
    val resourceId:String,
    val url: String
)