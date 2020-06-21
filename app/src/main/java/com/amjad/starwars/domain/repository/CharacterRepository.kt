package com.amjad.starwars.domain.repository

import androidx.paging.PagedList
import com.amjad.starwars.domain.models.CharacterDomainModel
import io.reactivex.Observable

interface CharacterRepository {

    fun searchCharacter(name: String): Observable<PagedList<CharacterDomainModel>>

    fun getCharacterDetails(id: String): Observable<CharacterDomainModel>
}