package com.amjad.starwars.domain.paging

import androidx.paging.DataSource
import com.amjad.starwars.data.models.CharacterDataModel
import javax.inject.Inject

class CharacterDataSourceFactory @Inject constructor(private val characterDataSource: CharacterDataSource):
    DataSource.Factory<String, CharacterDataModel>() {
    private lateinit var name: String

    override fun create(): DataSource<String, CharacterDataModel> {
        characterDataSource.setSearchParameter(name)
        return characterDataSource
    }

    fun setSearchParameter(name :String){
        this.name=name
    }
}