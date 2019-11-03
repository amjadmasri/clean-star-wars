package com.amjad.starwars.domain.useCase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import com.amjad.starwars.common.Resource
import com.amjad.starwars.common.Status
import javax.inject.Inject

class GetFilmsListUseCase @Inject constructor(private val filmRepository: FilmRepository) {
    fun getFilmsList(ids:List<String>):LiveData<Resource<FilmDomainModel>>{
        val mediatorLiveData =MediatorLiveData<Resource<FilmDomainModel>>()
        ids.forEach {
            val source =filmRepository.getFilmDetails(it)
            mediatorLiveData.addSource(source) { resource ->
                when(resource.status){
                    Status.SUCCESS->mediatorLiveData.setValue(Resource.success(resource.data))
                }
            }

        }

        return mediatorLiveData
    }
}