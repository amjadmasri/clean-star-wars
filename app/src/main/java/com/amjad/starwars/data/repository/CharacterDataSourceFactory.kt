package com.amjad.starwars.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.amjad.starwars.data.mappers.CharacterMapper
import com.amjad.starwars.data.remote.ApiService
import com.amjad.starwars.data.remote.CharacterRemoteSource
import com.amjad.starwars.data.remote.CharacterRemoteSourceImp
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import javax.inject.Inject

class CharacterDataSourceFactory @AssistedInject constructor(private val characterDataSourceFactory: CharacterDataSource.Factory,@Assisted private val name:String):
    DataSource.Factory<String, CharacterDomainModel>() {
    private val characterDataSource: CharacterDataSource by lazy{
        characterDataSourceFactory.create(name)
    }
    val sourceLiveData = MutableLiveData<CharacterDataSource>()

    override fun create(): DataSource<String, CharacterDomainModel> {
        sourceLiveData.postValue(characterDataSource)
        return characterDataSource
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(name: String): CharacterDataSourceFactory
    }
}