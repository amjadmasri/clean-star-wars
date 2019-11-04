package com.amjad.starwars.domain.useCase

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.domain.models.PagedListing
import com.amjad.starwars.domain.paging.CharacterDataSourceFactory
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(private val characterDataSourceFactory: CharacterDataSourceFactory)  {

    fun execute(characterName:String):PagedListing<CharacterDomainModel>{

        characterDataSourceFactory.setSearchParameter(characterName)
        val list =LivePagedListBuilder(characterDataSourceFactory,5)
            .build()

        return PagedListing(
            pagedList = list,
            networkState = Transformations.switchMap(characterDataSourceFactory.sourceLiveData) {
                it.networkState
            })
    }
}