package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.paging.CharacterDataSource
import com.amjad.starwars.domain.paging.CharacterDataSourceFactory
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(private val characterDataSourceFactory: CharacterDataSourceFactory)  {

    fun execute(characterName:String):LiveData<PagedList<CharacterDataModel>>{

        characterDataSourceFactory.setSearchParameter(characterName)
        return LivePagedListBuilder(characterDataSourceFactory,5)
            .build()
    }
}