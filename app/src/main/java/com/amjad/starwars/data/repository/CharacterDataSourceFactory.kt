package com.amjad.starwars.data.repository

import androidx.paging.DataSource
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class CharacterDataSourceFactory @AssistedInject constructor(
    private val characterDataSourceFactory: CharacterDataSource.Factory,
    @Assisted private val name: String
) :
    DataSource.Factory<String, CharacterDomainModel>() {
    private val characterDataSource: CharacterDataSource by lazy {
        characterDataSourceFactory.create(name)
    }

    override fun create(): DataSource<String, CharacterDomainModel> {
        return characterDataSource
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(name: String): CharacterDataSourceFactory
    }
}