package com.amjad.starwars.data.local

import com.amjad.starwars.data.local.dao.FilmDao
import com.amjad.starwars.data.models.FilmLocalDataModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FilmLocalSourceTest{

    private fun <T> anyObject(): T {
        return Mockito.any<T>()
    }

    @Mock
    lateinit var appDatabase: AppDatabase
    @Mock
    lateinit var filmDao:FilmDao

    lateinit var filmLocalSource: FilmLocalSourceImp
    @Before
    fun setup() {
        filmLocalSource= FilmLocalSourceImp(appDatabase)

        Mockito.`when`(appDatabase.filmDao()).thenAnswer {
            filmDao
        }
    }

    @Test
    fun testInsertFilmInvocation(){
        val filmLocalDataModel = FilmLocalDataModel(1,"created","director","edited","crawl","prod","date","title","1","url")
        filmLocalSource.insertFilm(filmLocalDataModel)

        verify(appDatabase).filmDao()
        verify(filmDao).insert(filmLocalDataModel)
    }

    @Test
    fun testGetFilmByIdInvocation(){

        filmLocalSource.getFilmById("1")

        verify(appDatabase).filmDao()
        verify(filmDao).getFilmByResourceId("1")
    }
}