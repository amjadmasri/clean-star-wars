package com.amjad.starwars.domain.useCase

import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetFilmsListUseCase @Inject constructor(private val filmRepository: FilmRepository) {
    fun execute(ids: List<String>): Observable<List<FilmDomainModel>> {
        val observableList = ArrayList<Observable<FilmDomainModel>>()
        ids.forEach {
            observableList.add(filmRepository.getFilmDetails(it))
        }
        return Observable.zip(observableList) {
            val mutableList: MutableList<FilmDomainModel> = mutableListOf()
            it.forEach { item ->
                mutableList.add(item as FilmDomainModel)
            }
            return@zip mutableList
        }


    }
}