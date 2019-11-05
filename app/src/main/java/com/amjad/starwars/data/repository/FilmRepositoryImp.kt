package com.amjad.starwars.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.amjad.starwars.data.local.FilmLocalSource
import com.amjad.starwars.data.mappers.FilmMapper
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.models.FilmRemoteDataModel
import com.amjad.starwars.data.models.NetworkBoundResource
import com.amjad.starwars.data.remote.FilmRemoteSource
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.domain.repository.FilmRepository
import com.amjad.starwars.common.Resource
import com.amjad.starwars.common.Status
import com.amjad.starwars.data.models.isExpiredAfterOneDay
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class FilmRepositoryImp @Inject constructor(
    private val filmRemoteSource: FilmRemoteSource,
    private val filmLocalSource: FilmLocalSource,
    private val filmMapper: FilmMapper
) : FilmRepository {
    override fun getFilmDetails(id: String): LiveData<Resource<FilmDomainModel>> {
      val result:LiveData<Resource<FilmLocalDataModel>> = object : NetworkBoundResource<FilmLocalDataModel, FilmRemoteDataModel>(){
            override fun saveCallResult(item: FilmRemoteDataModel): Completable {
                return filmLocalSource.insertFilm(filmMapper.mapRemoteToLocal(item))
            }

            override fun loadFromDb(): LiveData<FilmLocalDataModel> {

               return filmLocalSource.getFilmById(id)
            }

            override fun createCall(): Single<Response<FilmRemoteDataModel>> {
                return filmRemoteSource.getFilmDetails(id)
            }

          override fun shouldFetch(data: FilmLocalDataModel?): Boolean {
              return data?.isExpiredAfterOneDay() ?: true
          }
      }.asLiveData
       return Transformations.map(result){ input ->

           mapLocalToDomain(input)
       }

    }


    private fun mapLocalToDomain(input: Resource<FilmLocalDataModel>): Resource<FilmDomainModel> {
        Log.d("wisam","being called ?")
        return when(input.status){
            Status.SUCCESS-> Resource.success(input.data?.let { filmMapper.mapLocalToDomain(it) })
            Status.LOADING-> Resource.loading()
            Status.ERROR-> Resource.error(input.message.toString())
        }
    }

}