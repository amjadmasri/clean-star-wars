package com.amjad.starwars.data.models


import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

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
    val url: String,
    val localCreationDate:Long
)

fun FilmLocalDataModel.isExpiredAfterOneDay():Boolean{
    val currentDate = Date()
    val calendar = Calendar.getInstance()
    calendar.time= Date(localCreationDate)
    calendar.add(Calendar.HOUR,24)

    return currentDate >= calendar.time
}