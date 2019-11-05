package com.amjad.starwars.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import javax.inject.Inject

class CharacterDataSourceFactory @Inject constructor(private val characterDataSource: CharacterDataSource):
    DataSource.Factory<String, CharacterDomainModel>() {
    private lateinit var name: String
    val sourceLiveData = MutableLiveData<CharacterDataSource>()

    override fun create(): DataSource<String, CharacterDomainModel> {
        characterDataSource.setSearchParameter(name)
        sourceLiveData.postValue(characterDataSource)
        return characterDataSource
    }

    fun setSearchParameter(name :String){
        this.name=name
    }
}