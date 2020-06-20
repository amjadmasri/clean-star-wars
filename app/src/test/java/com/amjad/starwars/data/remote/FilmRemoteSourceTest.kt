package com.amjad.starwars.data.remote

import com.amjad.starwars.ObjectsProvider
import com.amjad.starwars.data.models.FilmRemoteDataModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
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

        `when`(apiService.getFilmDetails(ArgumentMatchers.anyString())).thenReturn(Single.just(response))
    }

    @Test
    fun testGetFilmDetailsInvocation(){
        filmRemoteSource.getFilmDetails("1")
        verify(apiService).getFilmDetails("1")
    }

    @Test
    fun testGetFilmDetailsResponseType(){
        filmRemoteSource.getFilmDetails("1")
            .test()
            .assertComplete()

        verify(apiService).getFilmDetails("1")
    }

    @Test
    fun testRemoteSourceSuccess(){
        val obj =ObjectsProvider.provideFilmRemoteModel()
        `when`(apiService.getFilmDetails("1")).thenReturn(Single.just(response))
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(obj)

        filmRemoteSource.getFilmDetails("1")
            .test()
            .assertValue(response)
    }

    @Test
    fun testRemoteSourceError(){
        val obj =ObjectsProvider.provideFilmRemoteModel()
        val throwable = mock(Throwable::class.java)
        `when`(apiService.getFilmDetails("1")).thenReturn(Single.error(throwable))
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(obj)

        filmRemoteSource.getFilmDetails("1")
            .test()
            .assertError(throwable)
    }
}