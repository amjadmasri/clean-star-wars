package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import com.amjad.starwars.common.Resource
import javax.inject.Inject

class GetFilmDetailsUseCase @Inject constructor(private val filmRepository: FilmRepository) {

    fun getFilmDetails(id:String):LiveData<Resource<FilmDomainModel>>{
        return filmRepository.getFilmDetails(id)
    }
}