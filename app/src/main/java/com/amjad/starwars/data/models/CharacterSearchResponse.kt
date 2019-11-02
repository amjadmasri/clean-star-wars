package com.amjad.starwars.data.models


import com.google.gson.annotations.SerializedName

data class CharacterSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val characters:List<CharacterDataModel>
)