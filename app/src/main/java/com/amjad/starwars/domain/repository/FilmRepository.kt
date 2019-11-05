package com.amjad.starwars.domain.repository

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.common.models.Resource

interface FilmRepository {

    fun getFilmDetails(id: String): LiveData<Resource<FilmDomainModel>>

}