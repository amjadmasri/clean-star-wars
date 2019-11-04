package com.amjad.starwars.data.remote

import com.amjad.starwars.ObjectsProvider
import com.amjad.starwars.data.local.dao.FilmDao
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.models.FilmRemoteDataModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class FilmRemoteSourceTest{

    @Mock
    lateinit var apiService: ApiService
    @Mock
    lateinit var response: Response<FilmRemoteDataModel>


    lateinit var filmRemoteSource: FilmRemoteSourceImp
    @Before
    fun setup() {
        filmRemoteSource= FilmRemoteSourceImp(apiService)

        Mockito.`when`(apiService.getfilmDetails(ArgumentMatchers.anyString())).thenReturn(Single.just(response))
    }

    @Test
    fun testGetFilmDetailsInvocation(){
        filmRemoteSource.getFilmDetails("1")
        verify(apiService).getfilmDetails("1")
    }

    @Test
    fun testGetFilmDetailsResponseType(){
        filmRemoteSource.getFilmDetails("1")
            .test()
            .assertComplete()

        verify(apiService).getfilmDetails("1")
    }

    @Test
    fun testRemoteSourceSuccess(){
        val obj =ObjectsProvider.provideFilmRemoteModel()
        Mockito.`when`(apiService.getfilmDetails("1")).thenReturn(Single.just(response))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(obj)

        filmRemoteSource.getFilmDetails("1")
            .test()
            .assertValue(response)
    }

    @Test
    fun testRemoteSourceError(){
        val obj =ObjectsProvider.provideFilmRemoteModel()
        val throwable = mock(Throwable::class.java)
        Mockito.`when`(apiService.getfilmDetails("1")).thenReturn(Single.error(throwable))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(obj)

        filmRemoteSource.getFilmDetails("1")
            .test()
            .assertError(throwable)
    }
}